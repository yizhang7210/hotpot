package com.hotpot.domain;


import lombok.Value;

import java.util.List;
import java.util.function.Predicate;

@Value(staticConstructor = "of")
public class ServiceObjective {
    private final ObjectiveId id;
    private final String description;
    private final Predicate<Service> isApplicableTo;
    private final List<Criterion<?>> criteria;
}
