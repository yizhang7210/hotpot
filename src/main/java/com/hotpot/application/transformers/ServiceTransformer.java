package com.hotpot.application.transformers;

import com.hotpot.application.defaults.DefaultServiceDto;
import com.hotpot.application.dtos.ServiceDto;
import com.hotpot.domain.Service;


public interface ServiceTransformer {

    default ServiceDto toServiceDTO(Service service) {
        return new DefaultServiceDto(service);
    }

}
