package com.hotpot.domain.providers;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.exceptions.HotpotUserError;

import java.util.Collection;

public interface ServiceMetricProvider {

    Collection<ServiceMetric<?>> getAllMetrics();

    ServiceMetric<?> getById(MetricId metricId);

    class ServiceMetricNotFoundErrorHotpot extends HotpotUserError {
        public ServiceMetricNotFoundErrorHotpot(MetricId metricId) {
            super(String.format("Service metrics with id %s is not found.", metricId.getValue()));
        }
    }

    class ServiceMetricLoadError extends InternalError {
        public ServiceMetricLoadError(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
