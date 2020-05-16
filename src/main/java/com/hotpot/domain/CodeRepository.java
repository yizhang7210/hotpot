package com.hotpot.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class CodeRepository {
    private final CodeRepositoryId repositoryId;
    private final Location location;
}
