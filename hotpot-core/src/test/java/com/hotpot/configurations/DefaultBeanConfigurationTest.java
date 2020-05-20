package com.hotpot.configurations;


import com.hotpot.application.defaults.DefaultServiceObjectiveResultTransformer;
import com.hotpot.application.defaults.DefaultServiceObjectiveTransformer;
import com.hotpot.application.defaults.DefaultServiceTransformer;
import com.hotpot.application.transformers.ServiceObjectiveResultTransformer;
import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.configurations.TestFallbackBeanConfiguration.TestServiceTransformer;
import com.hotpot.configurations.TestFallbackBeanConfiguration.TestServiceObjectiveTransformer;
import com.hotpot.configurations.TestFallbackBeanConfiguration.TestServiceObjectiveResultTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultBeanConfigurationTest {

    private ApplicationContextRunner contextRunner;

    @BeforeEach
    public void setup() {
        contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                TestRequiredBeanConfiguration.class,
                DefaultBeanConfiguration.class
            ));
    }

    @Test
    public void default_service_transformer_is_present_if_no_custom_bean() {
        this.contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(DefaultServiceTransformer.class);
            assertThat(context).doesNotHaveBean(ServiceObjectiveTransformer.class);
            assertThat(context).doesNotHaveBean(ServiceObjectiveResultTransformer.class);
        });
    }

    @Test
    public void custom_service_transformer_is_present_if_exists() {
        this.contextRunner
            .withBean(TestServiceTransformer.class)
            .run((context) -> {
                assertThat(context).hasSingleBean(TestServiceTransformer.class);
                assertThat(context).doesNotHaveBean(ServiceObjectiveTransformer.class);
                assertThat(context).doesNotHaveBean(ServiceObjectiveResultTransformer.class);
            });
    }

    @Test
    public void default_service_objective_transformers_are_present_if_no_custom_bean_and_enabled() {
        this.contextRunner
            .withPropertyValues("hotpot.slo.enabled=true")
            .run((context) -> {
                assertThat(context).hasSingleBean(DefaultServiceTransformer.class);
                assertThat(context).hasSingleBean(DefaultServiceObjectiveTransformer.class);
                assertThat(context).hasSingleBean(DefaultServiceObjectiveResultTransformer.class);
            });
    }

    @Test
    public void custom_service_objective_transformers_are_present_if_exist_and_enabled() {
        this.contextRunner
            .withPropertyValues("hotpot.slo.enabled=true")
            .withUserConfiguration(TestFallbackBeanConfiguration.class)
            .run((context) -> {
                assertThat(context).hasSingleBean(TestServiceTransformer.class);
                assertThat(context).hasSingleBean(TestServiceObjectiveTransformer.class);
                assertThat(context).hasSingleBean(TestServiceObjectiveResultTransformer.class);
            });
    }

}
