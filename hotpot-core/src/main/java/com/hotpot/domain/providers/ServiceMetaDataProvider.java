package com.hotpot.domain.providers;

import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetaData;
import com.hotpot.domain.Precedence;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface ServiceMetaDataProvider {

    ServiceMetaData getMetaDataById(ServiceId serviceId);

    default Map<ServiceId, ServiceMetaData> getMetaDataByIds(Collection<ServiceId> serviceIds) {
        return serviceIds
            .stream()
            .collect(Collectors.toMap(Function.identity(), this::getMetaDataById));
    }

    Precedence getPrecedence();

}
