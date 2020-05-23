package com.hotpot.domain;


import com.hotpot.domain.providers.ServiceDataProvider;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ServiceDataSourcePicker {

    private final List<ServiceDataProvider> serviceDataProviders;
    private final Map<ServiceMetric, ServiceDataProvider> cachedMap = new HashMap<>();

    public ServiceDataProvider getDataProvider(ServiceMetric metric) {

        if (cachedMap.containsKey(metric)) {
            return cachedMap.get(metric);
        }

        List<ServiceDataProvider> providers = serviceDataProviders.stream()
            .filter(sdp -> sdp.doesProvideFor(metric))
            .collect(Collectors.toList());

        if (providers.size() < 1) {
            throw new ServiceDataProvider.DataProviderNotFoundError(metric);
        } else if (providers.size() > 1) {
            throw new ServiceDataProvider.MultipleDataProviderError(metric);
        } else {
            ServiceDataProvider provider = providers.get(0);
            cachedMap.put(metric, provider);
            return provider;
        }

    }
}
