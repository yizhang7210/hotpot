package com.hotpot.configurations;


import com.hotpot.api.ServiceController;
import com.hotpot.api.ServiceObjectiveController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerConfigurationTest {

    private ApplicationContextRunner contextRunner;

    @BeforeEach
    public void setup() {
        contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                TestRequiredBeanConfiguration.class,
                DefaultBeanConfiguration.class,
                UseCaseConfiguration.class,
                UtilityBeanConfiguration.class,
                ControllerConfiguration.class
            ));
    }

    @Test
    public void controllers_are_present_when_api_is_enabled() {
        this.contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(ServiceController.class);
            assertThat(context).hasSingleBean(ControllerConfiguration.ErrorHandler.class);
        });
    }

    @Test
    public void controllers_are_not_present_when_api_is_disabled() {
        this.contextRunner
            .withPropertyValues("hotpot.web-api.enabled=false")
            .run((context) -> {
            assertThat(context).doesNotHaveBean(ServiceController.class);
            assertThat(context).doesNotHaveBean(ControllerConfiguration.ErrorHandler.class);
        });
    }

    @Test
    public void service_object_controller_is_present_when_slo_is_enabled() {
        this.contextRunner
            .withPropertyValues("hotpot.slo.enabled=true")
            .withUserConfiguration(TestFallbackBeanConfiguration.class)
            .run((context) -> {
                assertThat(context).hasSingleBean(ServiceController.class);
                assertThat(context).hasSingleBean(ServiceObjectiveController.class);
                assertThat(context).hasSingleBean(ControllerConfiguration.ErrorHandler.class);
            });
    }

}
