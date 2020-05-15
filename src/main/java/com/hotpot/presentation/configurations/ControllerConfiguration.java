package com.hotpot.presentation.configurations;

import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.application.usecases.ServiceUseCase;
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
    public ServiceController serviceController(
            ServiceUseCase serviceUseCase,
            ServiceTransformer serviceTransformer
    ) {
        LoggingUtils.logBeanName(log, ServiceController.class);
        return new ServiceController(serviceUseCase, serviceTransformer);
    }

}
