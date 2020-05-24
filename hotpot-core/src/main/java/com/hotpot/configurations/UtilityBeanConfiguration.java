package com.hotpot.configurations;

import com.hotpot.domain.ServiceDataSourcePicker;
import com.hotpot.domain.ServiceObjectiveEvaluator;
import com.hotpot.domain.providers.ServiceDataProvider;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.plugins.YamlServiceMetricProvider;
import com.hotpot.utils.LoggingUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.List;

@Slf4j
@Configuration
@AllArgsConstructor
public class UtilityBeanConfiguration {

    @Bean
    @ConditionalOnProperty(value = "hotpot.metrics.enabled", havingValue = "true")
    public ServiceDataSourcePicker serviceDataSourcePicker(
        List<ServiceDataProvider> serviceDataProviders
    ) {
        LoggingUtils.logBeanName(log, ServiceDataSourcePicker.class);
        return new ServiceDataSourcePicker(serviceDataProviders);
    }

    @Bean
    @ConditionalOnProperty(value = "hotpot.metrics.enabled", havingValue = "true")
    public ServiceObjectiveEvaluator serviceObjectiveEvaluator(
        ServiceDataSourcePicker serviceDataSourcePicker
    ) {
        LoggingUtils.logBeanName(log, ServiceObjectiveEvaluator.class);
        return new ServiceObjectiveEvaluator(serviceDataSourcePicker);
    }

    @Bean
    @ConditionalOnProperty(value = "hotpot.metrics.provider-type", havingValue = "yaml")
    public ServiceMetricProvider serviceMetricProvider(
        @Value("${hotpot.metrics.metrics-definition}") String metricsDefinitionLocation,
        DefaultResourceLoader defaultResourceLoader
    ) {
        LoggingUtils.logBeanName(log, YamlServiceMetricProvider.class);
        return new YamlServiceMetricProvider(metricsDefinitionLocation, defaultResourceLoader);
    }

}
