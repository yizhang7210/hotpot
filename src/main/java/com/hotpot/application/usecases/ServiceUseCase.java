package com.hotpot.application.usecases;

import com.hotpot.domain.ServiceId;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ServiceUseCase {

    private final ServiceIdentityProvider serviceIdentityProvider;

    public <T> List<T> getServiceIds(Function<ServiceId, T> transformer) {
        return serviceIdentityProvider.getServiceIds()
                .stream()
                .map(transformer)
                .collect(Collectors.toList());
    }

}
