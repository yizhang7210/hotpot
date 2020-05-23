package com.hotpot.configurations;

import com.hotpot.domain.ServiceDataSourcePicker;
import com.hotpot.domain.ServiceObjectiveEvaluator;
import com.hotpot.domain.providers.ServiceDataProvider;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.utils.LoggingUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ServiceDataSourcePicker serviceDataSourcePicker(
        List<ServiceDataProvider> serviceDataProviders
    ) {
        LoggingUtils.logBeanName(log, ServiceDataSourcePicker.class);
        return new ServiceDataSourcePicker(serviceDataProviders);
    }

    @Bean
    @ConditionalOnProperty(value = "hotpot.slo.enabled", havingValue = "true")
    public ServiceObjectiveEvaluator serviceObjectiveEvaluator(
        ServiceDataSourcePicker serviceDataSourcePicker,
        ServiceMetricProvider serviceMetricProvider
    ) {
        LoggingUtils.logBeanName(log, ServiceObjectiveEvaluator.class);
        return new ServiceObjectiveEvaluator(serviceDataSourcePicker, serviceMetricProvider);
    }


}
