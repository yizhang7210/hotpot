package com.hotpot.domain;


import java.util.Optional;

public interface ServiceObjective {
    ObjectiveId getId();
    Boolean isApplicable(Service service);
    String getDescription();
    Optional<ServiceObjectiveResult> getResult(Service service);
}
