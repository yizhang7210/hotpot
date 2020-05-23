package com.hotpotapp.providers;


import com.hotpot.domain.Criterion;
import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceMetricValue;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpotapp.domain.Metrics;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
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
                    new Criterion<Boolean>(
                        serviceMetricProvider.getByIdAndSpan(Metrics.DOCUMENTATION_PRESENT.getMetricId(), Duration.ZERO),
                        ServiceMetricValue::getValue)
                )
            ),
            ServiceObjective.of(
                ObjectiveId.of("release-frequency"),
                "Every service should released frequently",
                List.of(
                    new Criterion<Long>(
                        serviceMetricProvider.getByIdAndSpan(Metrics.AVERAGE_RELEASES_PER_DAY.getMetricId(), Duration.ofDays(28)),
                        value -> value.getValue() > 1),
                    new Criterion<Double>(
                        serviceMetricProvider.getByIdAndSpan(Metrics.OVERALL_ROLLBACK_PERCENTAGE.getMetricId(), Duration.ofDays(28)),
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
