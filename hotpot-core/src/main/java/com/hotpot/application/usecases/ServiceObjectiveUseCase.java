package com.hotpot.application.usecases;

import com.hotpot.domain.Criterion;
import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveResult;
import com.hotpot.domain.providers.ServiceDataProvider;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider.ObjectiveNotFoundError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class ServiceObjectiveUseCase {

    ServiceObjectiveProvider serviceObjectiveProvider;
    ServiceIdentityProvider serviceIdentityProvider;
    ServiceMetricProvider serviceMetricProvider;
    List<ServiceDataProvider> serviceDataProviders;

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

        Map<Criterion<?>, ServiceDataProvider> metricToProvider = objective.getCriteria()
            .stream()
            .collect(Collectors.toMap(
                Function.identity(),
                criterion -> getDataProvider(criterion.getMetric()).orElseThrow(RuntimeException::new)
            ));

        Collection<ServiceId> serviceIds = serviceIdentityProvider.getServiceIds();

        Map<String, T> result = new HashMap<>();
        for (ServiceId serviceId : serviceIds) {
            boolean success = true;
            for (Criterion<?> criterion : metricToProvider.keySet()) {
                success = success && criterion.getCheck().test(metricToProvider.get(criterion).getForService(criterion.getMetric(), serviceId));
            }
            result.put(serviceId.getValue(), transformer.apply(new ServiceObjectiveResult(
                serviceId, objectiveId, ServiceObjectiveResult.Status.fromBoolean(success)
            )));
        }

        return result;

    }

    private Optional<ServiceDataProvider> getDataProvider(ServiceMetric metric) {
        return serviceDataProviders.stream()
            .filter(sdp -> sdp.doesProvideFor(metric))
            .findFirst();
    }
}
