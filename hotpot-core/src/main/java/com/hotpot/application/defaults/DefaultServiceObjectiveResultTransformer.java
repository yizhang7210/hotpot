package com.hotpot.application.defaults;

import com.hotpot.application.transformers.ServiceObjectiveResultTransformer;
import com.hotpot.domain.ServiceObjectiveResult;

public class DefaultServiceObjectiveResultTransformer implements ServiceObjectiveResultTransformer<ServiceObjectiveResult> {

    @Override
    public ServiceObjectiveResult toDto(ServiceObjectiveResult result) {
        return result;
    }

}
