package com.hotpotapp.application.transformers;

import com.hotpot.application.transformers.ServiceMetricValueTransformer;
import com.hotpot.domain.ServiceMetricValue;
import com.hotpotapp.application.dtos.SimpleServiceMetricValueDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SimpleServiceMetricValueTransformer implements ServiceMetricValueTransformer<SimpleServiceMetricValueDto> {

    @Override
    public SimpleServiceMetricValueDto toDto(ServiceMetricValue<?> metricValue) {
        return new SimpleServiceMetricValueDto(
            metricValue.getMetric().getId().getValue(),
            metricValue.getMeasuredAt().toString(),
            metricValue.getValue().toString()
        );
    }
}
