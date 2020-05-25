package com.hotpotapp.providers;


import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceMetricValue;
import com.hotpot.domain.providers.ServiceDataProvider;
import com.hotpotapp.domain.Metrics;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@AllArgsConstructor
public class DocumentationDataProvider implements ServiceDataProvider {

    private static final List<MetricId> PROVIDED_METRICS = List.of(
        Metrics.DOCUMENTATION_LOCATION.getMetricId()
    );

    @Override
    public boolean doesProvideFor(MetricId metricId) {
        return PROVIDED_METRICS.contains(metricId);
    }

    @Override
    public <T> ServiceMetricValue<T> getForService(ServiceMetric<T> metric, ServiceId serviceId) {
        if (serviceId.getValue().startsWith("s")) {
            return null;
        }

        String location = "https://mydocs.com/" + serviceId.getValue();
        return new ServiceMetricValue<>(
            serviceId,
            metric,
            Instant.now(),
            metric.getMetricType().cast(location)
        );
    }
}
