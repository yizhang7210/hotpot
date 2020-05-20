package com.hotpot.configurations;

import com.hotpot.application.transformers.ServiceObjectiveResultTransformer;
import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.ServiceObjectiveResult;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Configuration
public class TestFallbackBeanConfiguration {

    public static class TestServiceTransformer implements ServiceTransformer<String, String> {
        @Override
        public String toDto(Service service) {
            return service.toString();
        }

        @Override
        public String toDetailedDto(Service service) {
            return service.toString();
        }
    }

    public static class TestServiceObjectiveTransformer implements ServiceObjectiveTransformer<String, String> {
        @Override
        public String toDto(ServiceObjective objective) {
            return objective.toString();
        }

        @Override
        public String toDetailedDto(ServiceObjective objective) {
            return objective.toString();
        }
    }

    public static class TestServiceObjectiveResultTransformer implements ServiceObjectiveResultTransformer<String> {
        @Override
        public String toDto(ServiceObjectiveResult result) {
            return result.toString();
        }
    }

    @Bean
    public ServiceTransformer<String, String> serviceTransformer() {
        return new TestServiceTransformer();
    }

    @Bean
    public ServiceObjectiveTransformer<String, String> serviceObjectiveTransformer() {
        return new TestServiceObjectiveTransformer();
    }

    @Bean
    public ServiceObjectiveResultTransformer<String> serviceObjectiveResultTransformer() {
        return new TestServiceObjectiveResultTransformer();
    }

    @Bean
    public ServiceObjectiveProvider serviceObjectiveProvider() {
        return new ServiceObjectiveProvider() {
            @Override
            public Collection<ServiceObjective> getObjectives() {
                return List.of();
            }

            @Override
            public Optional<ServiceObjective> getObjectiveById(ObjectiveId objectiveId) {
                return Optional.empty();
            }
        };
    }


}
