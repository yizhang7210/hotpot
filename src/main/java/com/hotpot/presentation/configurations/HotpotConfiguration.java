package com.hotpot.presentation.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({ControllerConfiguration.class, UseCaseConfiguration.class })
public class HotpotConfiguration {

}
