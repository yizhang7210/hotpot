package com.hotpot.configurations;

import com.hotpot.api.ServiceController;
import com.hotpot.api.ServiceObjectiveController;
import com.hotpot.application.transformers.ServiceObjectiveResultTransformer;
import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.application.usecases.ServiceObjectiveUseCase;
import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.domain.exceptions.UserError;
import com.hotpot.utils.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

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
    public <U, V, W> ServiceObjectiveController<U, V, W> serviceObjectiveController(
        ServiceObjectiveUseCase serviceObjectiveUseCase,
        ServiceObjectiveTransformer<U, V> serviceObjectiveTransformer,
        ServiceObjectiveResultTransformer<W> serviceObjectiveResultTransformer
    ) {
        LoggingUtils.logBeanName(log, ServiceObjectiveController.class);
        return new ServiceObjectiveController<>(
            serviceObjectiveUseCase,
            serviceObjectiveTransformer,
            serviceObjectiveResultTransformer
        );
    }

    @ControllerAdvice
    public static class ErrorHandler {

        @ExceptionHandler(UserError.class)
        public ResponseEntity<Map<String, String>> userErrorHandler(UserError error) {
            log.info("Handling a UserError from the api");
            return ResponseEntity
                .badRequest()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(error.toDto());
        }

    }

}
