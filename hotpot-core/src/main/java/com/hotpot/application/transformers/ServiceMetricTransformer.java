package com.hotpot.application.transformers;

import com.hotpot.domain.ServiceMetric;


public interface ServiceMetricTransformer<U, V> {
    U toDto(ServiceMetric<?> metric);
    V toDetailedDto(ServiceMetric<?> metric);
}
