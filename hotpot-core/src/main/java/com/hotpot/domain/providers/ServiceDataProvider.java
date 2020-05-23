package com.hotpot.domain.providers;

import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceMetricValue;
import com.hotpot.domain.exceptions.UserError;

public interface ServiceDataProvider {

    boolean doesProvideFor(ServiceMetric metric);

    <T> ServiceMetricValue<T> getForService(ServiceMetric metric, ServiceId serviceId);

    class DataProviderNotFoundError extends UserError {
        public DataProviderNotFoundError(ServiceMetric metric) {
            super(String.format("Cannot find a data provider for %s.", metric.toString()));
        }
    }

}
