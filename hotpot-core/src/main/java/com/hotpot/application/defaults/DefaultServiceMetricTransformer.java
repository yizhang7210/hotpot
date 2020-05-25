package com.hotpot.application.defaults;

import com.hotpot.application.transformers.ServiceMetricTransformer;
import com.hotpot.domain.ServiceMetric;

public class DefaultServiceMetricTransformer implements ServiceMetricTransformer<ServiceMetric<?>, ServiceMetric<?>> {

    @Override
    public ServiceMetric<?> toDto(ServiceMetric<?> metric) {
        return metric;
    }

    @Override
    public ServiceMetric<?> toDetailedDto(ServiceMetric<?> metric) {
        return metric;
    }

}
