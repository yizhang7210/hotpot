package com.hotpotapp.application.dtos;


import lombok.Value;

import java.util.Map;

@Value
public class SimpleServiceObjectiveDto {
    private String id;
    private String description;
    private Map<String, String> criteria;
}
