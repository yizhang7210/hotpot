package com.hotpot.application.transformers;

import com.hotpot.domain.ServiceMetricValue;


public interface ServiceMetricValueTransformer<T> {
    T toDto(ServiceMetricValue<?> metricValue);
}
