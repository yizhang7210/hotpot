package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Person {
    private String name;
    private TeamName team;
}
