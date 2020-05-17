package com.hotpotapp.domain;

import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceObjective;
import org.springframework.stereotype.Component;

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
}
