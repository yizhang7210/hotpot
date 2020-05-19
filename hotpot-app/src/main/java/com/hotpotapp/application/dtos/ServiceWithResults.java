package com.hotpotapp.application.dtos;


import com.hotpot.domain.Service;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ServiceWithResults {
    private final Service service;
    private final Map<String, SimpleServiceObjectiveResultDto> results;
}
