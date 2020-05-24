package com.hotpot.domain.providers;

import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.exceptions.HotpotUserError;

import java.util.Collection;
import java.util.Optional;

public interface ServiceObjectiveProvider {
    Collection<ServiceObjective> getObjectives();
    Optional<ServiceObjective> getObjectiveById(ObjectiveId objectiveId);

    class ObjectiveNotFoundErrorHotpot extends HotpotUserError {
        public ObjectiveNotFoundErrorHotpot(ObjectiveId objectiveId) {
            super(String.format("%s is not found.", objectiveId.getValue()));
        }
    }
}
