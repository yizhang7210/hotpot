package com.hotpotapp.providers;


import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DemoServiceObjectiveProvider implements ServiceObjectiveProvider {

    private final List<ServiceObjective> serviceObjectives;

    @Override
    public Collection<ServiceObjective> getObjectives() {
        return serviceObjectives;
    }

    @Override
    public Optional<ServiceObjective> getObjectiveById(ObjectiveId objectiveId) {
        return serviceObjectives.stream()
            .filter(o -> o.getId().equals(objectiveId))
            .findFirst();
    }
}
