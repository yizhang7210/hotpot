package com.hotpotapp.application.dtos;


import com.hotpot.domain.Service;
import lombok.Value;

import java.util.Map;

@Value
public class ServiceWithResults {
    private final Service service;
    private final Map<String, SimpleServiceObjectiveResultDto> results;
}
