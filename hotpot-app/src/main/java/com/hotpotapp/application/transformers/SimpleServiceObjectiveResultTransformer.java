package com.hotpotapp.application.transformers;

import com.hotpot.application.transformers.ServiceObjectiveResultTransformer;
import com.hotpot.domain.ServiceObjectiveResult;
import com.hotpot.domain.ServiceObjectiveResult.Status;
import com.hotpotapp.application.dtos.SimpleServiceObjectiveResultDto;
import org.springframework.stereotype.Component;

@Component
public class SimpleServiceObjectiveResultTransformer implements ServiceObjectiveResultTransformer<SimpleServiceObjectiveResultDto> {

    @Override
    public SimpleServiceObjectiveResultDto toDto(ServiceObjectiveResult result) {
        return new SimpleServiceObjectiveResultDto(
            result.getObjectiveId().getValue(),
            result.getServiceId().getValue(),
            result.getStatus()
                .map(s -> s.equals(Status.MET) ? "Pass" : "Fail")
                .orElse(null)
        );
    }
}
