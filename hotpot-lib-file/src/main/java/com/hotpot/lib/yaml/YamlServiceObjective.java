package com.hotpot.lib.yaml;


import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
public class YamlServiceObjective {

    private String id;
    private String description;
    private List<YamlCriterion> criteria;

    public ServiceObjective toServiceObjective(Collection<ServiceMetric<?>> metrics) {

        return ServiceObjective.of(
            ObjectiveId.of(id),
            description,
            r -> true,
            criteria.stream()
                .map(c -> c.toCriterion(getMetricById(metrics, c.getMetricId())))
                .collect(Collectors.toList())
        );
    }

    private ServiceMetric<?> getMetricById(Collection<ServiceMetric<?>> metrics, String metricId) {
        return metrics.stream()
            .filter(m -> m.getId().getValue().equals(metricId))
            .findFirst()
            .orElseThrow(() -> new ServiceObjectiveProvider.InvalidObjectiveError(
                    String.format("%s objective contain metricId %s that are not defined.", id, metricId))
            );
    }
}
