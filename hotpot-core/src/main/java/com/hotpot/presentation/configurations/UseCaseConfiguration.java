package com.hotpot.presentation.configurations;

import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceMetaDataProvider;
import com.hotpot.utils.LoggingUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@AllArgsConstructor
public class UseCaseConfiguration {

    ServiceIdentityProvider serviceIdentityProvider;
    List<ServiceMetaDataProvider> serviceMetaDataProviders;

    @Bean
    public ServiceUseCase serviceUseCase() {
        LoggingUtils.logBeanName(log, ServiceUseCase.class);
        return new ServiceUseCase(serviceIdentityProvider, serviceMetaDataProviders);
    }

}
