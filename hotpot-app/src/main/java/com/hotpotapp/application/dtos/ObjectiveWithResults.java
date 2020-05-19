package com.hotpotapp.application.dtos;


import com.hotpot.domain.ServiceObjective;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ObjectiveWithResults {
    private final ServiceObjective objective;
    private final Map<String, SimpleServiceObjectiveResultDto> results;
}
