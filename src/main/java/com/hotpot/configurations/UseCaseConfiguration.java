package com.hotpot.configurations;

import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public ServiceUseCase serviceUseCase(ServiceIdentityProvider serviceIdentityProvider) {
        return new ServiceUseCase(serviceIdentityProvider);
    }

}
