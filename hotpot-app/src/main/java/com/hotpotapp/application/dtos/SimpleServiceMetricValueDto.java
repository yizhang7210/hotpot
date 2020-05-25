package com.hotpotapp.application.dtos;


import lombok.Value;

@Value
public class SimpleServiceMetricValueDto {
    private String metricId;
    private String serviceId;
    private String measuredAt;
    private String value;
}
