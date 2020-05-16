package com.hotpot.domain;

import lombok.Data;

@Data
public class Service {
    private final ServiceId id;
    private final ServiceMetaData metaData;

    public Service(ServiceId serviceId) {
        this.id = serviceId;
        this.metaData = ServiceMetaData.builder().build();
    }

}
