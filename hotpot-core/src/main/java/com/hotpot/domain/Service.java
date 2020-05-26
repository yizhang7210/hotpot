package com.hotpot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Service {
    private final ServiceId id;
    private final ServiceMetaData metaData;

    public Service(ServiceId serviceId) {
        this.id = serviceId;
        this.metaData = ServiceMetaData.builder().build();
    }

}
