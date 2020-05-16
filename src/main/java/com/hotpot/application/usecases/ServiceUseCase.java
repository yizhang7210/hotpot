package com.hotpot.application.usecases;

import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceMetaDataProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
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
            service.getMetaData().mergeWith(mdp.getMetaDataById(serviceId));
        }

        return transformer.apply(service);
    }

    public <T> List<T> getServices(Function<Service, T> transformer) {

        Map<ServiceId, Service> services = serviceIdentityProvider
            .getServiceIds()
            .stream()
            .collect(Collectors.toMap(Function.identity(), Service::new));

        serviceMetaDataProviders.sort(Comparator.comparingInt(mdp -> mdp.getPrecedence().getValue()));

        for (ServiceMetaDataProvider mdp : serviceMetaDataProviders) {
            mdp.getMetaDataByIds(services.keySet())
                .forEach((sid, sMetaData) -> services.get(sid).getMetaData().mergeWith(sMetaData));
        }

        return services.values().stream().map(transformer).collect(Collectors.toList());
    }
}
