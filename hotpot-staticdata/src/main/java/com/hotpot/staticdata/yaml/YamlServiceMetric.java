package com.hotpot.staticdata.yaml;


import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.providers.ServiceMetricProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Data
@Slf4j
public class YamlServiceMetric {

    private String id;
    private String description;
    private String timeSpan;
    private String type;

    private static final String JAVA_LANG = "java.lang.";

    public ServiceMetric<?> toServiceMetric() {
        try {
            return new ServiceMetric<>(
                MetricId.of(id),
                description,
                Duration.parse(timeSpan),
                Class.forName(JAVA_LANG + type)
            );
        } catch (ClassNotFoundException e) {
            String message = String.format("Specified type %s for %s cannot be found.", type, id);
            log.error(message);
            throw new ServiceMetricProvider.ServiceMetricLoadError(message, e);
        }
    }

}
