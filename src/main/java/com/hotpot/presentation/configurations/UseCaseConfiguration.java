package com.hotpot.presentation.configurations;

import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.utils.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class UseCaseConfiguration {

    @Bean
    public ServiceUseCase serviceUseCase(ServiceIdentityProvider serviceIdentityProvider) {
        LoggingUtils.logBeanName(log, ServiceUseCase.class);
        return new ServiceUseCase(serviceIdentityProvider);
    }

}
