package com.hotpot.domain;


import lombok.Value;

import java.time.Duration;

@Value
public class ServiceMetric {
    private MetricId metricId;
    private String description;
    private Duration timeSpan;
}
