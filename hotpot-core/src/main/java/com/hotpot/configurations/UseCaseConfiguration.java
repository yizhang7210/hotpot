package com.hotpot.configurations;

import com.hotpot.application.usecases.ServiceMetricUseCase;
import com.hotpot.application.usecases.ServiceObjectiveUseCase;
import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.domain.ServiceConstructor;
import com.hotpot.domain.ServiceDataSourcePicker;
import com.hotpot.domain.ServiceObjectiveEvaluator;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpot.utils.LoggingUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class UseCaseConfiguration {

    private final ServiceIdentityProvider serviceIdentityProvider;
    private final ServiceConstructor serviceConstructor;

    @Bean
    public ServiceUseCase serviceUseCase() {
        LoggingUtils.logBeanName(log, ServiceUseCase.class);
        return new ServiceUseCase(serviceIdentityProvider, serviceConstructor);
    }

    @Bean
    @ConditionalOnProperty(value = "hotpot.metrics.enabled", havingValue = "true")
    public ServiceObjectiveUseCase serviceObjectiveUseCase(
        ServiceObjectiveProvider serviceObjectiveProvider,
        ServiceObjectiveEvaluator serviceObjectiveEvaluator
    ) {
        LoggingUtils.logBeanName(log, ServiceObjectiveUseCase.class);
        return new ServiceObjectiveUseCase(
            serviceIdentityProvider,
            serviceObjectiveProvider,
            serviceObjectiveEvaluator,
            serviceConstructor
        );
    }

    @Bean
    @ConditionalOnProperty(value = "hotpot.metrics.enabled", havingValue = "true")
    public ServiceMetricUseCase serviceMetricUseCase(
        ServiceMetricProvider serviceMetricProvider,
        ServiceDataSourcePicker serviceDataSourcePicker
    ) {
        LoggingUtils.logBeanName(log, ServiceMetricUseCase.class);
        return new ServiceMetricUseCase(serviceIdentityProvider, serviceMetricProvider, serviceDataSourcePicker);
    }


}
