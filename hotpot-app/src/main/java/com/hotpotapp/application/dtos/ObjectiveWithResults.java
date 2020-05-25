package com.hotpotapp.application.dtos;


import com.hotpot.domain.ServiceObjective;
import lombok.Value;

import java.util.Map;

@Value
public class ObjectiveWithResults {
    private final ServiceObjective objective;
    private final Map<String, SimpleServiceObjectiveResultDto> results;
}
