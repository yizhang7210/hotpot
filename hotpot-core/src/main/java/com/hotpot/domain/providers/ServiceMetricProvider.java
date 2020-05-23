package com.hotpot.domain.providers;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceMetric;

import java.time.Duration;
import java.util.Collection;

public interface ServiceMetricProvider {

    Collection<ServiceMetric> getAllMetrics();

    ServiceMetric getByIdAndDuration(MetricId metricId, Duration duration);
}
