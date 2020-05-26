package com.hotpotapp.application.transformers;

import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.domain.Criterion;
import com.hotpot.domain.ServiceConstructor;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveEvaluator;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpotapp.application.dtos.ObjectiveWithResults;
import com.hotpotapp.application.dtos.SimpleServiceObjectiveDto;
import com.hotpotapp.application.dtos.SimpleServiceObjectiveResultDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SimpleServiceObjectiveTransformer implements ServiceObjectiveTransformer<SimpleServiceObjectiveDto, ObjectiveWithResults> {

    private final ServiceIdentityProvider serviceIdentityProvider;
    private final SimpleServiceObjectiveResultTransformer resultTransformer;
    private final ServiceObjectiveEvaluator serviceObjectiveEvaluator;
    private final ServiceConstructor serviceConstructor;

    @Override
    public SimpleServiceObjectiveDto toDto(ServiceObjective objective) {
        return new SimpleServiceObjectiveDto(
            objective.getId().getValue(),
            objective.getDescription(),
            objective.getCriteria()
                .stream()
                .collect(Collectors.toMap(
                    c -> c.getMetric().getId().getValue(),
                    Criterion::getDescription
                ))
        );
    }

    @Override
    public ObjectiveWithResults toDetailedDto(ServiceObjective objective) {
        Map<String, SimpleServiceObjectiveResultDto> results = serviceConstructor
            .getServices(serviceIdentityProvider.getServiceIds())
            .stream()
            .map(service -> serviceObjectiveEvaluator.runOnService(objective, service))
            .flatMap(Optional::stream)
            .collect(Collectors.toMap(sor -> sor.getServiceId().getValue(), resultTransformer::toDto));

        return new ObjectiveWithResults(toDto(objective), results);
    }
}
