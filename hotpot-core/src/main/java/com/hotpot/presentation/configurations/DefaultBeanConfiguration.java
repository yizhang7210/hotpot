package com.hotpot.presentation.configurations;

import com.hotpot.application.defaults.DefaultServiceTransformer;
import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.domain.Service;
import com.hotpot.utils.LoggingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

}
