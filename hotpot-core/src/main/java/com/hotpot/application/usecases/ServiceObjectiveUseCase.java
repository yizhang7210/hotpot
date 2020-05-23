package com.hotpot.application.usecases;

import com.hotpot.domain.Criterion;
import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveResult;
import com.hotpot.domain.providers.ServiceDataProvider;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider.ObjectiveNotFoundError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class ServiceObjectiveUseCase {

    private final ServiceIdentityProvider serviceIdentityProvider;
    private final ServiceObjectiveProvider serviceObjectiveProvider;
    private final List<ServiceDataProvider> serviceDataProviders;

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

        Collection<ServiceId> serviceIds = serviceIdentityProvider.getServiceIds();

        return serviceIds
            .stream()
            .collect(Collectors.toMap(
                ServiceId::getValue,
                sid -> transformer.apply(getResultByServiceAndObjective(sid, objective))
            ));
    }

    private ServiceObjectiveResult getResultByServiceAndObjective(ServiceId serviceId, ServiceObjective objective) {
        boolean success = objective.getCriteria()
            .stream()
            .allMatch(criterion -> checkForService(criterion, serviceId));

        return new ServiceObjectiveResult(serviceId, objective.getId(), ServiceObjectiveResult.Status.fromBoolean(success));
    }

    private boolean checkForService(Criterion<?> criterion, ServiceId serviceId) {
        ServiceMetric metric = criterion.getMetric();

        return criterion.getCondition().test(
            getDataProvider(metric).orElseThrow(() -> new ServiceDataProvider.DataProviderNotFoundError(metric))
                .getForService(metric, serviceId)
        );
    }

    private Optional<ServiceDataProvider> getDataProvider(ServiceMetric metric) {
        return serviceDataProviders.stream()
            .filter(sdp -> sdp.doesProvideFor(metric))
            .findFirst();
    }

}
