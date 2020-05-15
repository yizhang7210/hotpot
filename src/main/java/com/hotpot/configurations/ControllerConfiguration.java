package com.hotpot.configurations;

import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.presentation.api.ServiceController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfiguration {

    @Bean
    public ServiceController serviceController(ServiceUseCase serviceUseCase) {
        return new ServiceController(serviceUseCase);
    }


}
