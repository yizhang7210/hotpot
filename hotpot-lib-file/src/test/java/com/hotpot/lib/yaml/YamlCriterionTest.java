package com.hotpot.lib.yaml;


import com.hotpot.domain.Criterion;
import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceMetricValue;
import com.hotpot.domain.providers.ServiceObjectiveProvider.InvalidObjectiveError;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
public class YamlCriterionTest {

    @ParameterizedTest
    @CsvSource(value = { "eq:false:true:false", "lt:false:false:true", "gt:true:false:false" }, delimiter = ':')
    public void valid_integer_criteria_should_work(String operator, boolean testFor3, boolean testFor1, boolean testFor0) {
        //Given
        ServiceId sId = ServiceId.of("sid");
        ServiceMetric<Integer> metric = new ServiceMetric<>(MetricId.of("id"), "", Duration.ZERO, Integer.class);

        // When
        YamlCriterion yamlCriterion = new YamlCriterion("id", operator + "/1", null);
        Criterion<Integer> criterion = yamlCriterion.toCriterion(metric);

        // Then
        assertEquals(testFor3, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), 3)));
        assertEquals(testFor1, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), 1)));
        assertEquals(testFor0, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), 0)));
    }

    @ParameterizedTest
    @CsvSource(value = {"lt:false:true", "gt:true:false" }, delimiter = ':')
    public void valid_double_criteria_should_work(String operator, boolean testFor3, boolean testFor1) {
        //Given
        ServiceId sId = ServiceId.of("sid");
        ServiceMetric<Double> metric = new ServiceMetric<>(MetricId.of("id"), "", Duration.ZERO, Double.class);

        // When
        YamlCriterion yamlCriterion = new YamlCriterion("id", operator + "/1.2", null);
        Criterion<Double> criterion = yamlCriterion.toCriterion(metric);

        // Then
        assertEquals(testFor3, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), 3.0)));
        assertEquals(testFor1, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), 1.0)));
    }

    @ParameterizedTest
    @CsvSource(value = {"true:true:false", "false:false:true" }, delimiter = ':')
    public void valid_boolean_criteria_should_work(String threshold, boolean testForTrue, boolean testForFalse) {
        //Given
        ServiceId sId = ServiceId.of("sid");
        ServiceMetric<Boolean> metric = new ServiceMetric<>(MetricId.of("id"), "", Duration.ZERO, Boolean.class);

        // When
        YamlCriterion yamlCriterion = new YamlCriterion("id", "eq/" + threshold, null);
        Criterion<Boolean> criterion = yamlCriterion.toCriterion(metric);

        // Then
        assertEquals(testForTrue, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), true)));
        assertEquals(testForFalse, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), false)));
    }

    @ParameterizedTest
    @CsvSource(value = { "fffff:false:true:false", "abcde:true:false:false", "kkk:false:false:false" }, delimiter = ':')
    public void valid_string_criteria_no_transform_should_work(String threshold, boolean result1, boolean result2, boolean result3) {
        //Given
        ServiceId sId = ServiceId.of("sid");
        ServiceMetric<String> metric = new ServiceMetric<>(MetricId.of("id"), "", Duration.ZERO, String.class);

        // When
        YamlCriterion yamlCriterion = new YamlCriterion("id", "eq/" + threshold, null);
        Criterion<String> criterion = yamlCriterion.toCriterion(metric);

        // Then
        assertEquals(result1, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), "abcde")));
        assertEquals(result2, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), "fffff")));
        assertEquals(result3, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), "")));
    }

    @ParameterizedTest
    @CsvSource(value = { "eq:false:true:false", "lt:false:false:true", "gt:true:false:false" }, delimiter = ':')
    public void valid_string_criteria_with_transform_should_work(String operator, boolean result1, boolean result2, boolean result3) {
        //Given
        ServiceId sId = ServiceId.of("sid");
        ServiceMetric<String> metric = new ServiceMetric<>(MetricId.of("id"), "", Duration.ZERO, String.class);

        // When
        YamlCriterion yamlCriterion = new YamlCriterion("id", operator + "/3", "length");
        Criterion<String> criterion = yamlCriterion.toCriterion(metric);

        // Then
        assertEquals(result1, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), "abcde")));
        assertEquals(result2, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), "123")));
        assertEquals(result3, criterion.getCondition().test(new ServiceMetricValue<>(sId, metric, Instant.now(), "")));
    }

    @Test
    public void eq_with_double_throws_exception() {
        //Given
        ServiceMetric<Double> metric = new ServiceMetric<>(MetricId.of("id"), "", Duration.ZERO, Double.class);

        // When
        YamlCriterion yamlCriterion = new YamlCriterion("id", "eq/3.0", null);

        // Then
        assertThrows(InvalidObjectiveError.class, () -> yamlCriterion.toCriterion(metric));
    }

    @ParameterizedTest
    @ValueSource(strings = {"lt", "gt"})
    public void non_eq_with_boolean_throws_exception(String operator) {
        //Given
        ServiceMetric<Boolean> metric = new ServiceMetric<>(MetricId.of("id"), "", Duration.ZERO, Boolean.class);

        // When
        YamlCriterion yamlCriterion = new YamlCriterion("id", operator + "/true", null);

        // Then
        assertThrows(InvalidObjectiveError.class, () -> yamlCriterion.toCriterion(metric));
    }

    @ParameterizedTest
    @ValueSource(classes = {Boolean.class, Double.class, Integer.class})
    public void transform_with_non_string_throws_exception(Class<?> clazz) {
        //Given
        ServiceMetric<?> metric = new ServiceMetric<>(MetricId.of("id"), "", Duration.ZERO, clazz);

        // When
        YamlCriterion yamlCriterion = new YamlCriterion("id",  "eq/anything", "notnull");

        // Then
        assertThrows(InvalidObjectiveError.class, () -> yamlCriterion.toCriterion(metric));
    }

    @ParameterizedTest
    @ValueSource(classes = {Instant.class, Duration.class})
    public void non_supported_types_throws_exception(Class<?> clazz) {
        //Given
        ServiceMetric<?> metric = new ServiceMetric<>(MetricId.of("id"), "", Duration.ZERO, clazz);

        // When
        YamlCriterion yamlCriterion = new YamlCriterion("id",  "eq/3", null);

        // Then
        assertThrows(InvalidObjectiveError.class, () -> yamlCriterion.toCriterion(metric));
    }


}
