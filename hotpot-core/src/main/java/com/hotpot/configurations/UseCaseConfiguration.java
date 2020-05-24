package com.hotpot.configurations;

import com.hotpot.application.usecases.ServiceObjectiveUseCase;
import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.domain.ServiceObjectiveEvaluator;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceMetaDataProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpot.utils.LoggingUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

    @Bean
    @ConditionalOnProperty(value = "hotpot.metrics.enabled", havingValue = "true")
    @ConditionalOnBean(ServiceObjectiveProvider.class)
    public ServiceObjectiveUseCase serviceObjectiveUseCase(
        ServiceObjectiveProvider serviceObjectiveProvider,
        ServiceObjectiveEvaluator serviceObjectiveEvaluator
    ) {
        LoggingUtils.logBeanName(log, ServiceObjectiveUseCase.class);
        return new ServiceObjectiveUseCase(serviceIdentityProvider, serviceObjectiveProvider, serviceObjectiveEvaluator);
    }

}
