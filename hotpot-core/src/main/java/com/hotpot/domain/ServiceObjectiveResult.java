package com.hotpot.domain;


import lombok.Data;
import lombok.Getter;

import java.util.Optional;

@Data
@Getter
public class ServiceObjectiveResult {
    private final ServiceId serviceId;
    private final ObjectiveId objectiveId;
    private final Status status;

    public enum Status {
        MET,
        NOT_MET;

        public static Status fromBoolean(boolean success) {
            return success ? MET : NOT_MET;
        }

    }

    public Optional<Status> getStatus() {
        return Optional.ofNullable(status);
    }
}
