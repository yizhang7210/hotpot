package com.hotpotapp.application.transformers;

import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveEvaluator;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpotapp.application.dtos.ObjectiveWithResults;
import com.hotpotapp.application.dtos.SimpleServiceObjectiveDto;
import com.hotpotapp.application.dtos.SimpleServiceObjectiveResultDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SimpleServiceObjectiveTransformer implements ServiceObjectiveTransformer<SimpleServiceObjectiveDto, ObjectiveWithResults> {

    private final ServiceIdentityProvider serviceIdentityProvider;
    private final SimpleServiceObjectiveResultTransformer resultTransformer;
    private final ServiceObjectiveEvaluator serviceObjectiveEvaluator;

    @Override
    public SimpleServiceObjectiveDto toDto(ServiceObjective objective) {
        return new SimpleServiceObjectiveDto(objective.getId().getValue(), objective.getDescription());
    }

    @Override
    public ObjectiveWithResults toDetailedDto(ServiceObjective objective) {
        Map<String, SimpleServiceObjectiveResultDto> results = serviceIdentityProvider.getServiceIds()
            .stream()
            .map(sid -> serviceObjectiveEvaluator.runOnService(objective, sid))
            .collect(Collectors.toMap(sor -> sor.getServiceId().getValue(), resultTransformer::toDto));

        return new ObjectiveWithResults(objective, results);
    }
}
