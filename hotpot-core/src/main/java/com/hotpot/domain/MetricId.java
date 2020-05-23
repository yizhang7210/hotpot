package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class MetricId {
    private final String value;
}
