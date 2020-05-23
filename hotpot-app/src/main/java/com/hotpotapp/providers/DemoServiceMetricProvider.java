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

    private final List<ServiceMetric> metrics = List.of(
        new ServiceMetric(
            Metrics.DOCUMENTATION_PRESENT.getMetricId(),
            "documentation",
            Duration.ZERO
        ),
        new ServiceMetric(
            Metrics.AVERAGE_RELEASES_PER_DAY.getMetricId(),
            "releases per day",
            Duration.ofDays(28)
        ),
        new ServiceMetric(
            Metrics.OVERALL_ROLLBACK_PERCENTAGE.getMetricId(),
            "rollbacks percentage",
            Duration.ofDays(28)
        )
    );

    @Override
    public Collection<ServiceMetric> getAllMetrics() {
        return metrics;
    }

    @Override
    public ServiceMetric getByIdAndSpan(MetricId metricId, Duration span) {
        return metrics.stream()
            .filter(m -> m.getMetricId().equals(metricId) && m.getTimeSpan().equals(span))
            .findFirst()
            .orElseThrow(() -> new ServiceMetricNotFoundError(metricId, span));
    }
}
