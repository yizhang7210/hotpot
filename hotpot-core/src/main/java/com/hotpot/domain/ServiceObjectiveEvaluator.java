package com.hotpot.domain;

import com.hotpot.domain.providers.ServiceMetricProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class ServiceObjectiveEvaluator {

    private final ServiceDataSourcePicker serviceDataSourcePicker;
    private final ServiceMetricProvider serviceMetricProvider;

    public ServiceObjectiveResult runOnService(ServiceObjective objective, ServiceId serviceId) {
        boolean success = objective.getCriteria()
            .stream()
            .allMatch(criterion -> checkForService(criterion, serviceId));

        return new ServiceObjectiveResult(serviceId, objective.getId(), ServiceObjectiveResult.Status.fromBoolean(success));
    }

    private <T> boolean checkForService(Criterion<T> criterion, ServiceId serviceId) {
        ServiceMetric<T> metric = serviceMetricProvider.getById(criterion.getMetricId());

        return criterion.getCondition().test(
            serviceDataSourcePicker.getDataProvider(criterion.getMetricId())
                .getForService(metric, serviceId)
        );
    }
}
