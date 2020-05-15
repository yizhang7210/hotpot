package com.hotpot.application.usecases;

import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceMetaDataProvider;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ServiceUseCase {

    private final ServiceIdentityProvider serviceIdentityProvider;
    private final List<ServiceMetaDataProvider> serviceMetaDataProviders;

    public <T> List<T> getServiceIds(Function<ServiceId, T> transformer) {
        return serviceIdentityProvider.getServiceIds()
                .stream()
                .map(transformer)
                .collect(Collectors.toList());
    }

    public <T> T getServiceById(ServiceId serviceId, Function<Service, T> transformer) {

        Service service = new Service(serviceId);

        serviceMetaDataProviders.sort(Comparator.comparingInt(mdp -> mdp.getPrecedence().getValue()));

        for (ServiceMetaDataProvider mdp : serviceMetaDataProviders) {
            service.setMetaData(mdp.getMetaDataById(serviceId));
        }

        return transformer.apply(service);
    }
}
