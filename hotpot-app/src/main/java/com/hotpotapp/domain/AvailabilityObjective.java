package com.hotpotapp.domain;

import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveResult;
import com.hotpot.domain.ServiceObjectiveResult.Status;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AvailabilityObjective implements ServiceObjective {
    @Override
    public ObjectiveId getId() {
        return ObjectiveId.of("availability");
    }

    @Override
    public Boolean isApplicable(Service service) {
        return service.getMetaData().getTier().map(t -> t.equals(Tiers.Tier_1.getTier())).orElse(false);
    }

    @Override
    public String getDescription() {
        return "Every service should be available.";
    }

    @Override
    public Optional<ServiceObjectiveResult> getResult(Service service) {
        return isApplicable(service) ?
            Optional.of(new ServiceObjectiveResult(service.getId(), getId(), Status.MET)) : Optional.empty();
    }
}
