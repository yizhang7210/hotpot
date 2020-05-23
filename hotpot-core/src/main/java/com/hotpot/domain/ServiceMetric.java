package com.hotpot.domain;


import lombok.Value;

import java.time.Duration;

@Value
public class ServiceMetric<T> {
    private MetricId id;
    private String description;
    private Duration timeSpan;
    private Class<T> metricType;
}
