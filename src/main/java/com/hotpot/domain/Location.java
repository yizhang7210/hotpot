package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Location {
    // TODO: Should it be URL?
    private final String value;
}
