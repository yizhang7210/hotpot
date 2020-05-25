package com.hotpot.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class ServiceObjectiveEvaluator {

    private final ServiceDataSourcePicker serviceDataSourcePicker;

    public Optional<ServiceObjectiveResult> runOnService(ServiceObjective objective, Service service) {

        if (!objective.getIsApplicableTo().test(service)) {
            return Optional.empty();
        }

        boolean success = objective.getCriteria()
            .stream()
            .allMatch(criterion -> checkForService(criterion, service.getId()));

        return Optional.of(
            new ServiceObjectiveResult(
                service.getId(),
                objective.getId(),
                ServiceObjectiveResult.Status.fromBoolean(success)
            )
        );
    }

    private <T> boolean checkForService(Criterion<T> criterion, ServiceId serviceId) {
        ServiceMetric<T> metric = criterion.getMetric();

        return criterion.getCondition().test(
            serviceDataSourcePicker.getDataProvider(metric.getId())
                .getForService(metric, serviceId)
        );
    }
}
