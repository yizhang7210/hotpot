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
        Metrics.DOCUMENTATION_PRESENT.getMetricId()
    );

    @Override
    public boolean doesProvideFor(MetricId metricId) {
        return PROVIDED_METRICS.contains(metricId);
    }

    @Override
    public <T> ServiceMetricValue<T> getForService(ServiceMetric<T> metric, ServiceId serviceId) {
        boolean success = serviceId.getValue().length() < 10;
        return new ServiceMetricValue<>(
            metric,
            Instant.now(),
            metric.getMetricType().cast(success)
        );
    }
}