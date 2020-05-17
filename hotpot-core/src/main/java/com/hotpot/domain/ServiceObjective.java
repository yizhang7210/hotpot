package com.hotpot.domain;


public interface ServiceObjective {
    ObjectiveId getId();
    Boolean isApplicable(Service service);
    String getDescription();
}
