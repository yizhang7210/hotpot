package com.hotpot.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.time.Duration;
import java.time.Instant;

@Value
public class DataPoint<T> {
    private ServiceId serviceId;
    private MetricId metricId;
    private Instant measuredAt;
    private Duration measuredFor;
    private T value;
}
