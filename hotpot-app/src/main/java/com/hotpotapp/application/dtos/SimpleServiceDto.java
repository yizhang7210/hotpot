package com.hotpotapp.application.dtos;


import lombok.Value;

@Value
public class SimpleServiceDto {
    private final String id;
    private final String tier;
    private final String owner;
    private final String channel;
    private final String version;
}
