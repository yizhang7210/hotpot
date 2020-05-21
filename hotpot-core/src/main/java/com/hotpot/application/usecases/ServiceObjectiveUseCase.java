package com.hotpot.application.usecases;

import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveResult;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider.ObjectiveNotFoundError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class ServiceObjectiveUseCase {

    ServiceObjectiveProvider serviceObjectiveProvider;
    ServiceUseCase serviceUseCase;

    public <T> List<T> getServiceObjectives(Function<ServiceObjective, T> transformer) {
        return serviceObjectiveProvider.getObjectives()
            .stream()
            .map(transformer)
            .collect(Collectors.toList());
    }

    public <T> T getObjectiveById(ObjectiveId objectiveId, Function<ServiceObjective, T> transformer) {
        return transformer.apply(
            serviceObjectiveProvider.getObjectiveById(objectiveId)
                .orElseThrow(() -> new ObjectiveNotFoundError(objectiveId))
        );
    }

    public <T> Map<String, T> getObjectiveResultsById(ObjectiveId objectiveId, Function<ServiceObjectiveResult, T> transformer) {

        ServiceObjective objective = serviceObjectiveProvider
            .getObjectiveById(objectiveId).orElseThrow(() -> new ObjectiveNotFoundError(objectiveId));

        return serviceUseCase.getServices(Function.identity())
            .stream()
            .map(objective::getResult)
            .flatMap(Optional::stream)
            .collect(Collectors.toMap(s -> s.getServiceId().getValue(), transformer));

    }
}
