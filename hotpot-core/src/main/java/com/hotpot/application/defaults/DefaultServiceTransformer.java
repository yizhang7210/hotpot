package com.hotpot.application.defaults;

import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.domain.Service;

public class DefaultServiceTransformer implements ServiceTransformer<Service, Service> {

    @Override
    public Service toDto(Service service) {
        return service;
    }

    @Override
    public Service toDetailedDto(Service service) {
        return service;
    }

}
