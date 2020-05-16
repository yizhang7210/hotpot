package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Person {
    private final String name;
    private final TeamName team;
}
