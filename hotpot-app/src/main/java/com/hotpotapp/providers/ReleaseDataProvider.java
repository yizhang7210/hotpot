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
public class ReleaseDataProvider implements ServiceDataProvider {

    private static final List<MetricId> PROVIDED_METRICS = List.of(
        Metrics.AVERAGE_RELEASES_PER_DAY.getMetricId(),
        Metrics.OVERALL_ROLLBACK_PERCENTAGE.getMetricId()
    );

    public Long getAverageReleases(ServiceId serviceId, Instant from, Instant to) {
        return Math.round(Math.random() * 100);
    }

    public Double getRollbackPercentage(ServiceId serviceId, Instant from, Instant to) {
        return Math.random();
    }

    @Override
    public boolean doesProvideFor(MetricId metricId) {
        return PROVIDED_METRICS.contains(metricId);
    }

    @Override
    public <T> ServiceMetricValue<T> getForService(ServiceMetric<T> metric, ServiceId serviceId) {
        Instant from = Instant.now().minus(metric.getTimeSpan());
        Instant to = Instant.now();

        if (metric.getId().equals(Metrics.AVERAGE_RELEASES_PER_DAY.getMetricId())) {
            return new ServiceMetricValue<>(
                metric,
                Instant.now(),
                metric.getMetricType().cast(getAverageReleases(serviceId, from, to))
            );

        } else if (metric.getId().equals(Metrics.OVERALL_ROLLBACK_PERCENTAGE.getMetricId())) {
            return new ServiceMetricValue<>(
                metric,
                Instant.now(),
                metric.getMetricType().cast(getRollbackPercentage(serviceId, from, to))
            );
        } else {
            return null;
        }
    }
}
