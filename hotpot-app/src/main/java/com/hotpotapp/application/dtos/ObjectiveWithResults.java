package com.hotpotapp.application.dtos;


import lombok.Value;

import java.util.Map;

@Value
public class ObjectiveWithResults {
    private final SimpleServiceObjectiveDto objective;
    private final Map<String, SimpleServiceObjectiveResultDto> results;
}
