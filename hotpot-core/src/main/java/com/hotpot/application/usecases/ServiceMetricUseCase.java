package com.hotpot.application.usecases;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceDataSourcePicker;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceMetricValue;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.domain.providers.ServiceMetricProvider.ServiceMetricNotFoundError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
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

    public <T> List<T> getServiceMetricValues(
        MetricId metricId, ServiceId serviceId, Function<ServiceMetricValue<?>, T> transformer
    ) {
        Predicate<ServiceMetric<?>> metricFilter = metricId == null ? m -> true : m -> m.getId().equals(metricId);
        Predicate<ServiceId> serviceFilter = serviceId == null ? s -> true : s -> s.equals(serviceId);

        List<ServiceMetric<?>> metrics = serviceMetricProvider.getAllMetrics()
            .stream().filter(metricFilter).collect(Collectors.toList());

        List<ServiceId> serviceIds = serviceIdentityProvider.getServiceIds()
            .stream().filter(serviceFilter).collect(Collectors.toList());

        List<T> values = new ArrayList<>();

        for (ServiceMetric<?> metric : metrics) {
            for (ServiceId sId : serviceIds) {
                ServiceMetricValue<?> value = serviceDataSourcePicker.getDataProvider(metric.getId()).getForService(metric, sId);
                if (value != null) {
                    values.add(transformer.apply(value));
                }
            }
        }

        return values;

    }

}
