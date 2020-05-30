package com.hotpot.lib.yaml;

import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpot.utils.LoggingUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

@Slf4j
@Configuration
@AllArgsConstructor
@ConditionalOnProperty(value = "hotpot.static-data.enabled", havingValue = "true")
public class AutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "hotpot.static-data.type", havingValue = "yaml")
    public ServiceMetricProvider serviceMetricProvider(
        @Value("${hotpot.static-data.metrics-location}") String metricsDefinitionLocation,
        DefaultResourceLoader defaultResourceLoader
    ) {
        LoggingUtils.logBeanName(log, YamlServiceMetricProvider.class);
        return new YamlServiceMetricProvider(metricsDefinitionLocation, defaultResourceLoader);
    }

    @Bean
    @ConditionalOnProperty(value = "hotpot.static-data.type", havingValue = "yaml")
    public ServiceObjectiveProvider serviceObjectiveProvider(
        @Value("${hotpot.static-data.objectives-location}") String objectiveDefinitionLocation,
        DefaultResourceLoader defaultResourceLoader,
        ServiceMetricProvider serviceMetricProvider
    ) {
        LoggingUtils.logBeanName(log, YamlServiceObjectiveProvider.class);
        return new YamlServiceObjectiveProvider(objectiveDefinitionLocation, defaultResourceLoader, serviceMetricProvider);
    }

    @Bean
    @ConditionalOnProperty(value = "hotpot.static-data.type", havingValue = "yaml")
    public YamlServiceProvider serviceProvider(
        @Value("${hotpot.static-data.services-location}") String serviceDefinitionLocation,
        DefaultResourceLoader defaultResourceLoader,
        @Value("${hotpot.static-data.metadata-precedence}") String precedence
    ) {
        LoggingUtils.logBeanName(log, YamlServiceProvider.class);
        return new YamlServiceProvider(serviceDefinitionLocation, defaultResourceLoader, precedence);
    }

}
