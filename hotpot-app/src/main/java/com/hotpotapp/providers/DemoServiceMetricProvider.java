package com.hotpotapp.providers;


import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpotapp.domain.Metrics;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class DemoServiceMetricProvider implements ServiceMetricProvider {

    private final List<ServiceMetric<?>> metrics = List.of(
        new ServiceMetric<>(
            Metrics.DOCUMENTATION_PRESENT.getMetricId(),
            "documentation",
            Duration.ZERO,
            Boolean.class
        ),
        new ServiceMetric<>(
            Metrics.AVERAGE_RELEASES_PER_DAY.getMetricId(),
            "releases per day",
            Duration.ofDays(28),
            Long.class
        ),
        new ServiceMetric<>(
            Metrics.OVERALL_ROLLBACK_PERCENTAGE.getMetricId(),
            "rollbacks percentage",
            Duration.ofDays(28),
            Double.class
        )
    );

    @Override
    public Collection<ServiceMetric<?>> getAllMetrics() {
        return metrics;
    }

    @Override
    public <T> ServiceMetric<T> getByIdAndSpan(MetricId metricId, Duration span) {
        return (ServiceMetric<T>) metrics.stream()
            .filter(m -> m.getId().equals(metricId) && m.getTimeSpan().equals(span))
            .findFirst()
            .orElseThrow(() -> new ServiceMetricNotFoundError(metricId, span));
    }
}
