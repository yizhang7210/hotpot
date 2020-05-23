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

    public ServiceMetricValue<Long> getAverageReleases(ServiceId serviceId, ServiceMetric metric, Instant from, Instant to) {
        return new ServiceMetricValue<>(
            metric,
            to,
            Math.round(Math.random() * 100)
        );
    }

    public ServiceMetricValue<Double> getRollbackPercentage(ServiceId serviceId, ServiceMetric metric, Instant from, Instant to) {
        return new ServiceMetricValue<>(
            metric,
            to,
            Math.random()
        );
    }

    @Override
    public boolean doesProvideFor(ServiceMetric metric) {
        return PROVIDED_METRICS.contains(metric.getMetricId());
    }

    @Override
    //TODO: Figure out this generic type thing
    public ServiceMetricValue<? extends Number> getForService(ServiceMetric metric, ServiceId serviceId) {
        if (metric.getMetricId().equals(Metrics.AVERAGE_RELEASES_PER_DAY.getMetricId())) {
            return getAverageReleases(serviceId, metric, Instant.now().minus(metric.getTimeSpan()), Instant.now());
        } else if (metric.getMetricId().equals(Metrics.OVERALL_ROLLBACK_PERCENTAGE.getMetricId())) {
            return getRollbackPercentage(serviceId, metric, Instant.now().minus(metric.getTimeSpan()), Instant.now());
        } else {
            return null;
        }
    }
}
