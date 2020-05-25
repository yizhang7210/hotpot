package com.hotpot.application.defaults;

import com.hotpot.application.transformers.ServiceMetricValueTransformer;
import com.hotpot.domain.ServiceMetricValue;

public class DefaultServiceMetricValueTransformer implements ServiceMetricValueTransformer<ServiceMetricValue<?>> {

    @Override
    public ServiceMetricValue<?> toDto(ServiceMetricValue<?> metricValue) {
        return metricValue;
    }

}
