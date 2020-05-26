package com.hotpotapp.application.dtos;


import lombok.Value;

import java.util.Map;

@Value
public class MetricWithResults {
    private final SimpleServiceMetricDto metric;
    private final Map<String, SimpleServiceMetricValueDto> results;
}
