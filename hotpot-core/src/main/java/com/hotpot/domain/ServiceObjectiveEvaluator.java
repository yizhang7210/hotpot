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

    private boolean checkForService(Criterion<?> criterion, ServiceId serviceId) {
        ServiceMetric metric = criterion.getMetric();

        return criterion.getCondition().test(
            serviceDataSourcePicker.getDataProvider(metric)
                .getForService(metric, serviceId)
        );
    }
}
