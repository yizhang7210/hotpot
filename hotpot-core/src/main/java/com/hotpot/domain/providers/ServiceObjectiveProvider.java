package com.hotpot.domain.providers;

import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceObjective;

import java.util.Collection;
import java.util.Optional;

public interface ServiceObjectiveProvider {
    Collection<ServiceObjective> getObjectives();
    Optional<ServiceObjective> getObjectiveById(ObjectiveId objectiveId);

    class ObjectiveNotFoundError extends RuntimeException {
        public ObjectiveNotFoundError(ObjectiveId objectiveId) {
            super(String.format("%s is not found.", objectiveId.getValue()));
        }
    }
}
