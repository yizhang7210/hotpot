package com.hotpot.application.defaults;

import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.domain.Service;

public class DefaultServiceTransformer implements ServiceTransformer<Service> {

    @Override
    public Service toDTO(Service service) {
        return service;
    }

}
