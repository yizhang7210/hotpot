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
    private final Map<MetricId, ServiceDataProvider> cachedMap = new HashMap<>();

    public ServiceDataProvider getDataProvider(MetricId metricId) {

        if (cachedMap.containsKey(metricId)) {
            return cachedMap.get(metricId);
        }

        List<ServiceDataProvider> providers = serviceDataProviders.stream()
            .filter(sdp -> sdp.doesProvideFor(metricId))
            .collect(Collectors.toList());

        if (providers.size() < 1) {
            throw new ServiceDataProvider.DataProviderNotFoundErrorHotpot(metricId);
        } else if (providers.size() > 1) {
            throw new ServiceDataProvider.MultipleDataProviderErrorHotpot(metricId);
        } else {
            ServiceDataProvider provider = providers.get(0);
            cachedMap.put(metricId, provider);
            return provider;
        }

    }
}
