package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Version {
    String value;
}
