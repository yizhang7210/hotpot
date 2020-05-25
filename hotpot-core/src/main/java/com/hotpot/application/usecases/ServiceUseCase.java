package com.hotpot.application.usecases;

import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceConstructor;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class ServiceUseCase {

    private final ServiceIdentityProvider serviceIdentityProvider;
    private final ServiceConstructor serviceConstructor;

    public <T> List<T> getServiceIds(Function<ServiceId, T> transformer) {
        return serviceIdentityProvider.getServiceIds()
            .stream()
            .map(transformer)
            .collect(Collectors.toList());
    }

    public <T> T getServiceById(ServiceId serviceId, Function<Service, T> transformer) {
        return transformer.apply(serviceConstructor.getServiceById(serviceId));
    }

    public <T> List<T> getServices(Function<Service, T> transformer) {
        return serviceConstructor.getServices(serviceIdentityProvider.getServiceIds())
            .stream()
            .map(transformer)
            .collect(Collectors.toList())
            ;

    }
}
