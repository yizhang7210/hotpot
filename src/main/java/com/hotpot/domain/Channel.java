package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Channel {
    private final String value;
}
