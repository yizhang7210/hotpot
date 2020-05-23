package com.hotpot.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class ServiceObjectiveEvaluator {

    private final ServiceDataSourcePicker serviceDataSourcePicker;

    public ServiceObjectiveResult runOnService(ServiceObjective objective, ServiceId serviceId) {
        boolean success = objective.getCriteria()
            .stream()
            .allMatch(criterion -> checkForService(criterion, serviceId));

        return new ServiceObjectiveResult(serviceId, objective.getId(), ServiceObjectiveResult.Status.fromBoolean(success));
    }

    private <T> boolean checkForService(Criterion<T> criterion, ServiceId serviceId) {
        ServiceMetric<T> metric = criterion.getMetric();

        return criterion.getCondition().test(
            serviceDataSourcePicker.getDataProvider(metric.getId())
                .getForService(metric, serviceId)
        );
    }
}