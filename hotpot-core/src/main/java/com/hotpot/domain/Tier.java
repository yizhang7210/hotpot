package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Tier {
    private final String value;
    private final String description;
}
