package com.hotpot.application.defaults;

import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.domain.ServiceObjective;

public class DefaultServiceObjectiveTransformer implements ServiceObjectiveTransformer<ServiceObjective, ServiceObjective> {

    @Override
    public ServiceObjective toDto(ServiceObjective serviceObjective) {
        return serviceObjective;
    }

    @Override
    public ServiceObjective toDetailedDto(ServiceObjective serviceObjective) {
        return serviceObjective;
    }

}
