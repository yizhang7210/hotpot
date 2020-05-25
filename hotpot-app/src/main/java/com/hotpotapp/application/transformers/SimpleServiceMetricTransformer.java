package com.hotpotapp.application.transformers;

import com.hotpot.application.transformers.ServiceMetricTransformer;
import com.hotpot.domain.ServiceMetric;
import com.hotpotapp.application.dtos.SimpleServiceMetricDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SimpleServiceMetricTransformer implements ServiceMetricTransformer<SimpleServiceMetricDto, SimpleServiceMetricDto> {

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
    public SimpleServiceMetricDto toDetailedDto(ServiceMetric<?> metric) {
        return new SimpleServiceMetricDto(
            metric.getId().getValue(),
            metric.getDescription(),
            metric.getTimeSpan().toString(),
            metric.getMetricType().getSimpleName()
        );
    }
}
