package com.hotpot.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Service {
    private final ServiceId id;
    private ServiceMetaData metaData;
}
