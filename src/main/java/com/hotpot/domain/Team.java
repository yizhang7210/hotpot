package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Team {
    private TeamName name;
}
