package com.hotpot.presentation.configuration;

import com.hotpot.domain.providers.ServiceMetadataProvider;
import com.hotpot.presentation.api.ServiceController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfiguration {

    @Bean
    public ServiceController serviceController(ServiceMetadataProvider serviceMetadataProvider) {
        return new ServiceController(serviceMetadataProvider);
    }


}
