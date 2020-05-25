package com.hotpotapp.application.dtos;


import com.hotpot.domain.ServiceMetric;
import lombok.Value;

import java.util.Map;

@Value
public class MetricWithResults<T> {
    private final ServiceMetric<T> metric;
    private final Map<String, SimpleServiceMetricValueDto> results;
}
