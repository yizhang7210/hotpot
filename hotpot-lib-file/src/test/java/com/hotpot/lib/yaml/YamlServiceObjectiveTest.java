package com.hotpot.lib.yaml;


import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.Tier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
public class YamlServiceObjectiveTest {

    @Test
    public void no_filters_should_include_all_services() {
        //Given
        YamlServiceObjective yso = new YamlServiceObjective();
        yso.setCriteria(List.of());
        Service service1 = new Service(ServiceId.of("service1"));
        Service service2 = new Service(ServiceId.of("service2"));
        service2.getMetaData().setTier(Tier.of("tier 2", "tier 2 description"));

        // When
        ServiceObjective objective = yso.toServiceObjective(null);

        // Then
        assertTrue(objective.getIsApplicableTo().test(service1));
        assertTrue(objective.getIsApplicableTo().test(service2));
    }

    @Test
    public void only_with_include_filters_should_include_correct_services() {
        //Given
        YamlServiceObjective yso = new YamlServiceObjective();
        yso.setCriteria(List.of());
        yso.setInclude(List.of(new YamlFilter("id", List.of("service1"))));

        Service service1 = new Service(ServiceId.of("service1"));
        Service service2 = new Service(ServiceId.of("service2"));
        service2.getMetaData().setTier(Tier.of("tier 2", "tier 2 description"));

        // When
        ServiceObjective objective = yso.toServiceObjective(null);

        // Then
        assertTrue(objective.getIsApplicableTo().test(service1));
        assertFalse(objective.getIsApplicableTo().test(service2));
    }

    @Test
    public void only_with_exclude_filters_should_exclude_correct_services() {
        //Given
        YamlServiceObjective yso = new YamlServiceObjective();
        yso.setCriteria(List.of());
        yso.setExclude(List.of(new YamlFilter("id", List.of("service1"))));

        Service service1 = new Service(ServiceId.of("service1"));
        Service service2 = new Service(ServiceId.of("service2"));
        service2.getMetaData().setTier(Tier.of("tier 2", "tier 2 description"));

        // When
        ServiceObjective objective = yso.toServiceObjective(null);

        // Then
        assertFalse(objective.getIsApplicableTo().test(service1));
        assertTrue(objective.getIsApplicableTo().test(service2));
    }

    @Test
    public void multiple_include_filters_should_include_services_that_satisfy_any() {
        //Given
        YamlServiceObjective yso = new YamlServiceObjective();
        yso.setCriteria(List.of());
        yso.setInclude(List.of(
            new YamlFilter("id", List.of("service1")),
            new YamlFilter("tier", List.of("tier 2"))
        ));

        Service service1 = new Service(ServiceId.of("service1"));
        Service service2 = new Service(ServiceId.of("service2"));
        service2.getMetaData().setTier(Tier.of("tier 2", "tier 2 description"));

        // When
        ServiceObjective objective = yso.toServiceObjective(null);

        // Then
        assertTrue(objective.getIsApplicableTo().test(service1));
        assertTrue(objective.getIsApplicableTo().test(service2));
    }

    @Test
    public void multiple_exclude_filters_should_exclude_services_that_satisfy_any() {
        //Given
        YamlServiceObjective yso = new YamlServiceObjective();
        yso.setCriteria(List.of());
        yso.setExclude(List.of(
            new YamlFilter("id", List.of("service1")),
            new YamlFilter("tier", List.of("tier 2"))
        ));

        Service service1 = new Service(ServiceId.of("service1"));
        Service service2 = new Service(ServiceId.of("service2"));
        service2.getMetaData().setTier(Tier.of("tier 2", "tier 2 description"));

        // When
        ServiceObjective objective = yso.toServiceObjective(null);

        // Then
        assertFalse(objective.getIsApplicableTo().test(service1));
        assertFalse(objective.getIsApplicableTo().test(service2));
    }

    @Test
    public void include_a_list_of_the_same_field_works_correctly() {
        //Given
        YamlServiceObjective yso = new YamlServiceObjective();
        yso.setCriteria(List.of());
        yso.setInclude(List.of(
            new YamlFilter("id", List.of("service1", "service2"))
        ));

        Service service1 = new Service(ServiceId.of("service1"));
        Service service2 = new Service(ServiceId.of("service2"));
        service2.getMetaData().setTier(Tier.of("tier 2", "tier 2 description"));
        Service service3 = new Service(ServiceId.of("service3"));

        // When
        ServiceObjective objective = yso.toServiceObjective(null);

        // Then
        assertTrue(objective.getIsApplicableTo().test(service1));
        assertTrue(objective.getIsApplicableTo().test(service2));
        assertFalse(objective.getIsApplicableTo().test(service3));
    }

    @Test
    public void exclude_a_list_of_the_same_field_works_correctly() {
        //Given
        YamlServiceObjective yso = new YamlServiceObjective();
        yso.setCriteria(List.of());
        yso.setExclude(List.of(
            new YamlFilter("id", List.of("service1", "service2"))
        ));

        Service service1 = new Service(ServiceId.of("service1"));
        Service service2 = new Service(ServiceId.of("service2"));
        service2.getMetaData().setTier(Tier.of("tier 2", "tier 2 description"));
        Service service3 = new Service(ServiceId.of("service3"));

        // When
        ServiceObjective objective = yso.toServiceObjective(null);

        // Then
        assertFalse(objective.getIsApplicableTo().test(service1));
        assertFalse(objective.getIsApplicableTo().test(service2));
        assertTrue(objective.getIsApplicableTo().test(service3));
    }

    @Test
    public void when_in_conflict_exclude_takes_precedence() {
        //Given
        YamlServiceObjective yso = new YamlServiceObjective();
        yso.setCriteria(List.of());
        yso.setInclude(List.of(
            new YamlFilter("id", List.of("service1", "service2"))
        ));
        yso.setExclude(List.of(
            new YamlFilter("tier", List.of("tier 2"))
        ));

        Service service1 = new Service(ServiceId.of("service1"));
        Service service2 = new Service(ServiceId.of("service2"));
        service2.getMetaData().setTier(Tier.of("tier 2", "tier 2 description"));
        Service service3 = new Service(ServiceId.of("service3"));

        // When
        ServiceObjective objective = yso.toServiceObjective(null);

        // Then
        assertTrue(objective.getIsApplicableTo().test(service1));
        assertFalse(objective.getIsApplicableTo().test(service2));
        assertFalse(objective.getIsApplicableTo().test(service3));
    }

}
