package com.hotpot.configurations;

import com.hotpot.domain.ServiceDataSourcePicker;
import com.hotpot.domain.providers.ServiceDataProvider;
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
public class UtilityBeanConfiguration {

    @Bean
    @ConditionalOnProperty(value = "hotpot.slo.enabled", havingValue = "true")
    @ConditionalOnBean(ServiceObjectiveProvider.class)
    public ServiceDataSourcePicker serviceObjectiveUseCase(
        List<ServiceDataProvider> serviceDataProviders
    ) {
        LoggingUtils.logBeanName(log, ServiceDataSourcePicker.class);
        return new ServiceDataSourcePicker(serviceDataProviders);
    }


}
