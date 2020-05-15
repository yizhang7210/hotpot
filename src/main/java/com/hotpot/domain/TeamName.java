package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class TeamName {
    private final String value;
}
