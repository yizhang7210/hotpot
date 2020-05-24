package com.hotpot.domain.providers;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceMetricValue;
import com.hotpot.domain.exceptions.HotpotUserError;

public interface ServiceDataProvider {

    boolean doesProvideFor(MetricId metricId);

    <T> ServiceMetricValue<T> getForService(ServiceMetric<T> metric, ServiceId serviceId);

    class DataProviderNotFoundError extends HotpotUserError {
        public DataProviderNotFoundError(MetricId metricId) {
            super(String.format("Cannot find a data provider for %s.", metricId.getValue()));
        }
    }

    class MultipleDataProviderError extends HotpotUserError {
        public MultipleDataProviderError(MetricId metricId) {
            super(String.format("There are multiple data providers for %s.", metricId.getValue()));
        }
    }

}
