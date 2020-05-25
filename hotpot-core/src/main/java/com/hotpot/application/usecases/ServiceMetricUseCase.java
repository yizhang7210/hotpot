package com.hotpot.application.usecases;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceDataSourcePicker;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceMetricValue;
import com.hotpot.domain.providers.ServiceDataProvider;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.domain.providers.ServiceMetricProvider.ServiceMetricNotFoundError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class ServiceMetricUseCase {

    private final ServiceIdentityProvider serviceIdentityProvider;
    private final ServiceMetricProvider serviceMetricProvider;
    private final ServiceDataSourcePicker serviceDataSourcePicker;

    public <T> List<T> getServiceMetrics(Function<ServiceMetric<?>, T> transformer) {
        return serviceMetricProvider.getAllMetrics()
            .stream()
            .map(transformer)
            .collect(Collectors.toList());
    }

    public <T> T getServiceMetricById(MetricId metricId, Function<ServiceMetric<?>, T> transformer) {
        return transformer.apply(
            serviceMetricProvider.getMetricById(metricId)
                .orElseThrow(() -> new ServiceMetricNotFoundError(metricId))
        );
    }

    public <T> Map<String, T> getServiceMetricValuesById(MetricId metricId, Function<ServiceMetricValue<?>, T> transformer) {

        ServiceDataProvider dataProvider = serviceDataSourcePicker.getDataProvider(metricId);
        ServiceMetric<?> metric = serviceMetricProvider
            .getMetricById(metricId).
            orElseThrow(() -> new ServiceMetricNotFoundError(metricId));

        return serviceIdentityProvider.getServiceIds()
            .stream()
            .map(sId -> dataProvider.getForService(metric, sId))
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(
                metricValue -> metricValue.getServiceId().getValue(),
                transformer
            ));
    }

}
