package com.hotpot.domain;


import com.hotpot.domain.providers.ServiceMetaDataProvider;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ServiceConstructor {

    private final List<ServiceMetaDataProvider> serviceMetaDataProviders;

    public Service getServiceById(ServiceId serviceId) {

        Service service = new Service(serviceId);

        serviceMetaDataProviders.sort(Comparator.comparingInt(mdp -> mdp.getPrecedence().getValue()));

        for (ServiceMetaDataProvider mdp : serviceMetaDataProviders) {
            service.getMetaData().mergeWith(mdp.getMetaDataById(serviceId));
        }

        return service;
    }

    public Collection<Service> getServices(Collection<ServiceId> serviceIds) {

        Map<ServiceId, Service> services = serviceIds
            .stream()
            .collect(Collectors.toMap(Function.identity(), Service::new));

        serviceMetaDataProviders.sort(Comparator.comparingInt(mdp -> mdp.getPrecedence().getValue()));

        for (ServiceMetaDataProvider mdp : serviceMetaDataProviders) {
            mdp.getMetaDataByIds(services.keySet())
                .forEach((sid, sMetaData) -> services.get(sid).getMetaData().mergeWith(sMetaData));
        }

        return services.values();
    }


}
