package com.hotpotapp.application.transformers;

import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpotapp.application.dtos.ObjectiveWithResults;
import com.hotpotapp.application.dtos.ServiceWithResults;
import com.hotpotapp.application.dtos.SimpleServiceObjectiveResultDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SimpleServiceObjectiveTransformer implements ServiceObjectiveTransformer<ServiceObjective, ObjectiveWithResults> {

    ServiceUseCase serviceUseCase;
    SimpleServiceObjectiveResultTransformer resultTransformer;

    @Override
    public ServiceObjective toDto(ServiceObjective objective) {
        return objective;
    }

    @Override
    public ObjectiveWithResults toDetailedDto(ServiceObjective objective) {
        Map<String, SimpleServiceObjectiveResultDto> results = serviceUseCase.getServices(Function.identity())
            .stream()
            .map(objective::getResult)
            .flatMap(Optional::stream)
            .collect(Collectors.toMap(sor -> sor.getServiceId().getValue(), resultTransformer::toDto));

        return new ObjectiveWithResults(objective, results);
    }
}
