package com.hotpot.lib.yaml;

import com.hotpot.domain.providers.ServiceMetricProvider;
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
public class AutoConfiguration {

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
