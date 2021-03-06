package com.hotpot.domain;


import lombok.Value;

import java.time.Instant;

@Value
public class ServiceMetricValue<T> {
    private ServiceId serviceId;
    private ServiceMetric<T> metric;
    private Instant measuredAt;
    private T value;
}
