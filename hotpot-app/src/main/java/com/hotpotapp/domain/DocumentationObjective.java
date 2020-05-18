package com.hotpotapp.domain;

import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveResult;
import com.hotpot.domain.ServiceObjectiveResult.Status;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DocumentationObjective implements ServiceObjective {
    @Override
    public ObjectiveId getId() {
        return ObjectiveId.of("documentation");
    }

    @Override
    public Boolean isApplicable(Service service) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Every service should have documentation.";
    }

    @Override
    public Optional<ServiceObjectiveResult> getResult(Service service) {
        // Arbitrary test to decide the status
        ServiceObjectiveResult.Status status = service.getId().getValue().length() > 15 ? Status.MET : Status.NOT_MET;
        return Optional.of(new ServiceObjectiveResult(service.getId(), getId(), status));
    }
}
