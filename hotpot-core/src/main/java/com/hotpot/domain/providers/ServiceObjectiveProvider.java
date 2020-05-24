package com.hotpot.domain.providers;

import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.exceptions.HotpotInternalError;
import com.hotpot.domain.exceptions.HotpotUserError;

import java.util.Collection;
import java.util.Optional;

public interface ServiceObjectiveProvider {
    Collection<ServiceObjective> getObjectives();
    Optional<ServiceObjective> getObjectiveById(ObjectiveId objectiveId);

    class ObjectiveNotFoundError extends HotpotUserError {
        public ObjectiveNotFoundError(ObjectiveId objectiveId) {
            super(String.format("%s is not found.", objectiveId.getValue()));
        }
    }

    class InvalidObjectiveError extends HotpotUserError {
        public InvalidObjectiveError(String message) {
            super(String.format("There are invalid objectives: %s", message));
        }
    }

    class ServiceObjectiveLoadError extends HotpotInternalError {
        public ServiceObjectiveLoadError(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
