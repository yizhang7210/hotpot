package com.hotpotapp.application.dtos;


import lombok.Value;

@Value
public class SimpleServiceMetricDto {
    private String id;
    private String description;
    private String timeSpan;
    private String type;
}
