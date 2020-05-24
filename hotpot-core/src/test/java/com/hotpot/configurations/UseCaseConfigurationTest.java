package com.hotpot.configurations;


import com.hotpot.application.usecases.ServiceObjectiveUseCase;
import com.hotpot.application.usecases.ServiceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class UseCaseConfigurationTest {

    private ApplicationContextRunner contextRunner;

    @BeforeEach
    public void setup() {
        contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                TestRequiredBeanConfiguration.class,
                DefaultBeanConfiguration.class,
                UtilityBeanConfiguration.class,
                UseCaseConfiguration.class
            ));
    }

    @Test
    public void service_use_case_is_present_always() {
        this.contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(ServiceUseCase.class);
            assertThat(context).doesNotHaveBean(ServiceObjectiveUseCase.class);
        });
    }

    @Test
    public void service_objective_use_case_is_present_when_enabled() {
        this.contextRunner
            .withPropertyValues("hotpot.metrics.enabled=true")
            .withUserConfiguration(TestFallbackBeanConfiguration.class)
            .run((context) -> {
                assertThat(context).hasSingleBean(ServiceUseCase.class);
                assertThat(context).hasSingleBean(ServiceObjectiveUseCase.class);
            });
    }

}
