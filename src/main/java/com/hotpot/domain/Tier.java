package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Tier {
    String value;
    String description;
}
