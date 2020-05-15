package com.hotpot.application.defaults;

import com.hotpot.application.dtos.ServiceDto;
import com.hotpot.domain.Service;
import lombok.Data;

@Data
public class DefaultServiceDto implements ServiceDto {
    private final Service service;
}
