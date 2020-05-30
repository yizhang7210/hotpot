package com.hotpot.application.usecases;

import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceConstructor;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveEvaluator;
import com.hotpot.domain.ServiceObjectiveResult;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider.ObjectiveNotFoundError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class ServiceObjectiveUseCase {

    private final ServiceIdentityProvider serviceIdentityProvider;
    private final ServiceObjectiveProvider serviceObjectiveProvider;
    private final ServiceObjectiveEvaluator serviceObjectiveEvaluator;
    private final ServiceConstructor serviceConstructor;

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

    public <T> List<T> getServiceObjectiveResults(
        ObjectiveId objectiveId, ServiceId serviceId, Function<ServiceObjectiveResult, T> transformer
    ) {
        Predicate<ServiceObjective> objectiveFilter = objectiveId == null ? o -> true : o -> o.getId().equals(objectiveId);
        Predicate<ServiceId> serviceFilter = serviceId == null ? s -> true : s -> s.equals(serviceId);

        List<ServiceObjective> objectives = serviceObjectiveProvider.getObjectives()
            .stream().filter(objectiveFilter).collect(Collectors.toList());

        Collection<Service> services = serviceConstructor.getServices(
            serviceIdentityProvider.getServiceIds().stream().filter(serviceFilter).collect(Collectors.toList())
        );

        List<T> results = new ArrayList<>();
        for (ServiceObjective objective : objectives) {
            for (Service service : services) {
                Optional<ServiceObjectiveResult> result = serviceObjectiveEvaluator.runOnService(objective, service);
                result.ifPresent(r -> results.add(transformer.apply(r)));
            }
        }
        return results;
    }

}
