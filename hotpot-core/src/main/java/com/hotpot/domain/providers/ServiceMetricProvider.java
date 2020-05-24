package com.hotpot.domain.providers;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.exceptions.HotpotInternalError;
import com.hotpot.domain.exceptions.HotpotUserError;

import java.util.Collection;
import java.util.Optional;

public interface ServiceMetricProvider {

    Collection<ServiceMetric<?>> getAllMetrics();

    Optional<ServiceMetric<?>> getById(MetricId metricId);

    class ServiceMetricNotFoundError extends HotpotUserError {
        public ServiceMetricNotFoundError(MetricId metricId) {
            super(String.format("Service metrics with id %s is not found.", metricId.getValue()));
        }
    }

    class ServiceMetricLoadError extends HotpotInternalError {
        public ServiceMetricLoadError(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
