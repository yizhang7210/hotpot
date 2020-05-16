package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class CodeRepository {
    private CodeRepositoryId repositoryId;
    private Location location;
}