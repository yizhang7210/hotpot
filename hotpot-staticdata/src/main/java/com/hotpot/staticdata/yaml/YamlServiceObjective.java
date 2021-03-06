package com.hotpot.staticdata.yaml;


import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class YamlServiceObjective {

    private String id;
    private String description;
    private List<YamlCriterion> criteria;
    private List<YamlFilter> include;
    private List<YamlFilter> exclude;

    public ServiceObjective toServiceObjective(Collection<ServiceMetric<?>> metrics) {
        return ServiceObjective.of(
            ObjectiveId.of(id),
            description,
            toIsApplicableTo(),
            criteria.stream()
                .map(c -> c.toCriterion(getMetricById(metrics, c.getMetricId())))
                .collect(Collectors.toList())
        );
    }

    private Predicate<Service> toIsApplicableTo() {
        Predicate<Service> nonNull = Objects::nonNull;
        return nonNull.and(service -> {
            boolean success = true;
            if (include != null) {
                success = include.stream().anyMatch(f -> f.toPredicate().test(service));
            }
            if (exclude != null) {
                success = success && exclude.stream().noneMatch(f -> f.toPredicate().test(service));
            }
            return success;
        });
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
