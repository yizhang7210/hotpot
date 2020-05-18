package com.hotpot.presentation.configurations;

import com.hotpot.application.defaults.DefaultServiceObjectiveResultTransformer;
import com.hotpot.application.defaults.DefaultServiceObjectiveTransformer;
import com.hotpot.application.defaults.DefaultServiceTransformer;
import com.hotpot.application.transformers.ServiceObjectiveResultTransformer;
import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveResult;
import com.hotpot.utils.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DefaultBeanConfiguration {

    @Bean
    @ConditionalOnMissingBean(ServiceTransformer.class)
    public ServiceTransformer<Service, Service> serviceTransformer() {
        LoggingUtils.logBeanName(log, DefaultServiceTransformer.class);
        return new DefaultServiceTransformer();
    }

    @Bean
    @ConditionalOnMissingBean(ServiceObjectiveTransformer.class)
    @ConditionalOnProperty(value = "hotpot.slo.enabled", havingValue = "true")
    public ServiceObjectiveTransformer<ServiceObjective, ServiceObjective> serviceObjectiveTransformer() {
        LoggingUtils.logBeanName(log, DefaultServiceObjectiveTransformer.class);
        return new DefaultServiceObjectiveTransformer();
    }

    @Bean
    @ConditionalOnMissingBean(ServiceObjectiveResultTransformer.class)
    @ConditionalOnProperty(value = "hotpot.slo.enabled", havingValue = "true")
    public ServiceObjectiveResultTransformer<ServiceObjectiveResult> serviceObjectiveResultTransformer() {
        LoggingUtils.logBeanName(log, DefaultServiceObjectiveResultTransformer.class);
        return new DefaultServiceObjectiveResultTransformer();
    }

}
