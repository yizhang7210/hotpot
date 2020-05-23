package com.hotpot.domain.providers;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.exceptions.UserError;

import java.time.Duration;
import java.util.Collection;

public interface ServiceMetricProvider {

    Collection<ServiceMetric> getAllMetrics();

    ServiceMetric getByIdAndSpan(MetricId metricId, Duration span);

    class ServiceMetricNotFoundError extends UserError {
        public ServiceMetricNotFoundError(MetricId metricId, Duration span) {
            super(String.format("Service metrics with id %s and duration %s is not found.",
                metricId.getValue(), span.toString()));
        }
    }

}
