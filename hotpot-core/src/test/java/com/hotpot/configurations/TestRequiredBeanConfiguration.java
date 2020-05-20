package com.hotpot.configurations;

import com.hotpot.domain.providers.ServiceIdentityProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TestRequiredBeanConfiguration {

    @Bean
    public ServiceIdentityProvider serviceIdentityProvider() {
        return List::of;
    }

}
