package com.hotpot.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({
    ControllerConfiguration.class,
    UseCaseConfiguration.class,
    DefaultBeanConfiguration.class
})
public class HotpotConfiguration {

}
