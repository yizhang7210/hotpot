package com.hotpotapp.providers;


import com.hotpot.domain.Criterion;
import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpotapp.domain.Metrics;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DemoServiceObjectiveProvider implements ServiceObjectiveProvider {

    private final ServiceMetricProvider serviceMetricProvider;

    @Override
    public Collection<ServiceObjective> getObjectives() {
        return List.of(
            ServiceObjective.of(
                ObjectiveId.of("documentation"),
                "Every service should have documentation",
                List.of(
                    new Criterion<>(
                        (ServiceMetric<String>) serviceMetricProvider.getById(Metrics.DOCUMENTATION_LOCATION.getMetricId()),
                        value -> value.getValue().length() > 0)
                )
            ),
            ServiceObjective.of(
                ObjectiveId.of("release-frequency"),
                "Every service should released frequently",
                List.of(
                    new Criterion<>(
                        (ServiceMetric<Double>) serviceMetricProvider.getById(Metrics.AVERAGE_RELEASES_PER_DAY.getMetricId()),
                        value -> value.getValue() > 1),
                    new Criterion<>(
                        (ServiceMetric<Double>) serviceMetricProvider.getById(Metrics.OVERALL_ROLLBACK_PERCENTAGE.getMetricId()),
                        value -> value.getValue() < 0.3)
                )
            )
        );
    }

    @Override
    public Optional<ServiceObjective> getObjectiveById(ObjectiveId objectiveId) {
        return getObjectives().stream()
            .filter(o -> o.getId().equals(objectiveId))
            .findFirst();
    }
}
