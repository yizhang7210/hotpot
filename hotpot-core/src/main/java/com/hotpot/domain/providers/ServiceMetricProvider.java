package com.hotpot.domain.providers;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.exceptions.UserError;

import java.util.Collection;

public interface ServiceMetricProvider {

    Collection<ServiceMetric<?>> getAllMetrics();

    <T> ServiceMetric<T> getById(MetricId metricId);

    class ServiceMetricNotFoundError extends UserError {
        public ServiceMetricNotFoundError(MetricId metricId) {
            super(String.format("Service metrics with id %s is not found.", metricId.getValue()));
        }
    }

}
