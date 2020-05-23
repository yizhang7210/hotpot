package com.hotpot.domain;


import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class ServiceObjective {
    private final ObjectiveId id;
    private final String description;
    private final List<Criterion<?>> criteria;
}
