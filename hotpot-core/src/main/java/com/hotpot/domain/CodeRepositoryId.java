package com.hotpot.domain;


import lombok.Value;

@Value(staticConstructor = "of")
public class CodeRepositoryId {
    private final String value;
}
