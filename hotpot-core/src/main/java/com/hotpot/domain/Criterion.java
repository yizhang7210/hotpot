package com.hotpot.domain;


import lombok.Value;

import java.util.function.Predicate;

@Value
public class Criterion<T> {
    private ServiceMetric<T> metric;
    private Predicate<ServiceMetricValue<T>> condition;
}
