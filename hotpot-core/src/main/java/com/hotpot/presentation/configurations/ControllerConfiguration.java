package com.hotpot.presentation.configurations;

import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.application.usecases.ServiceObjectiveUseCase;
import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.presentation.api.ServiceObjectiveController;
import com.hotpot.presentation.api.ServiceController;
import com.hotpot.utils.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "hotpot.web-api.enabled", havingValue = "true", matchIfMissing = true)
public class ControllerConfiguration {

    @Bean
    public <U, V> ServiceController<U, V> serviceController(
        ServiceUseCase serviceUseCase,
        ServiceTransformer<U, V> serviceTransformer
    ) {
        LoggingUtils.logBeanName(log, ServiceController.class);
        return new ServiceController<>(serviceUseCase, serviceTransformer);
    }

    @Bean
    @ConditionalOnProperty(value = "hotpot.slo.enabled", havingValue = "true")
    public <U, V> ServiceObjectiveController<U, V> serviceObjectiveController(
        ServiceObjectiveUseCase serviceObjectiveUseCase,
        ServiceObjectiveTransformer<U, V> serviceObjectiveTransformer
    ) {
        LoggingUtils.logBeanName(log, ServiceObjectiveController.class);
        return new ServiceObjectiveController<>(serviceObjectiveUseCase, serviceObjectiveTransformer);
    }
}
