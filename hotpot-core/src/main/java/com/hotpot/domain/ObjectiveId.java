package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class ObjectiveId {
    private final String value;
}
