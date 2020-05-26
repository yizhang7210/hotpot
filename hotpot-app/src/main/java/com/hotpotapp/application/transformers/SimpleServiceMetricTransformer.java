package com.hotpotapp.application.transformers;

import com.hotpot.application.transformers.ServiceMetricTransformer;
import com.hotpot.domain.ServiceDataSourcePicker;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.providers.ServiceDataProvider;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpotapp.application.dtos.MetricWithResults;
import com.hotpotapp.application.dtos.SimpleServiceMetricDto;
import com.hotpotapp.application.dtos.SimpleServiceMetricValueDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SimpleServiceMetricTransformer implements ServiceMetricTransformer<SimpleServiceMetricDto, MetricWithResults> {

    private final ServiceDataSourcePicker serviceDataSourcePicker;
    private final ServiceIdentityProvider serviceIdentityProvider;
    private final SimpleServiceMetricValueTransformer metricValueTransformer;

    @Override
    public SimpleServiceMetricDto toDto(ServiceMetric<?> metric) {
        return new SimpleServiceMetricDto(
            metric.getId().getValue(),
            metric.getDescription(),
            metric.getTimeSpan().toString(),
            metric.getMetricType().getSimpleName()
        );
    }

    @Override
    public MetricWithResults toDetailedDto(ServiceMetric<?> metric) {

        ServiceDataProvider dataProvider = serviceDataSourcePicker.getDataProvider(metric.getId());

        Map<String, SimpleServiceMetricValueDto> results = serviceIdentityProvider.getServiceIds()
            .stream()
            .map(sId -> dataProvider.getForService(metric, sId))
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(
                metricValue -> metricValue.getServiceId().getValue(),
                metricValueTransformer::toDto)
            );

        return new MetricWithResults(toDto(metric), results);

    }
}
