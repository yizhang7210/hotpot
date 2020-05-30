package com.hotpot.lib.yaml;


import com.hotpot.domain.Criterion;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceMetricValue;
import com.hotpot.domain.providers.ServiceObjectiveProvider.InvalidObjectiveError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class YamlCriterion {
    private String metricId;
    private String description;
    private String condition;
    private String transform;

    private static final String SPLITTER = "/";

    private enum Operator {
        eq,
        lt,
        gt
    }

    public <T> Criterion<T> toCriterion(ServiceMetric<T> metric) {
        Predicate<ServiceMetricValue<T>> nonNull = Objects::nonNull;
        return new Criterion<>(
            metric,
            description,
            nonNull.and(toCondition(metric))
        );
    }

    @SneakyThrows
    private <T> Predicate<ServiceMetricValue<T>> toCondition(ServiceMetric<T> metric) {
        String[] elements = condition.split("/");
        if (elements.length != 2) {
            throw new InvalidObjectiveError("The condition has to be 2 parts, split by " + SPLITTER);
        }

        Operator operator = Operator.valueOf(elements[0]);
        String threshold = elements[1];

        Class<?> type = metric.getMetricType();

        if (!String.class.equals(type) && transform != null) {
            throw new InvalidObjectiveError("Only supported transform on String type");
        }

        if (Integer.class.equals(type)) {
            return integerPredicate(operator, threshold);
        }
        if (Double.class.equals(type)) {
            return doublePredicate(operator, threshold);
        }
        if (Boolean.class.equals(type)) {
            return booleanPredicate(operator, threshold);
        }
        if (String.class.equals(type)) {
            return stringPredicate(operator, threshold);
        }
        throw new InvalidObjectiveError("Only supported metric types are Integer, Double, Boolean, and String.");
    }

    private <T> Predicate<ServiceMetricValue<T>> integerPredicate(Operator operator, String threshold) {
        switch(operator) {
            case eq:
                return tValue -> tValue.getValue().equals(Integer.parseInt(threshold));
            case lt:
                return tValue -> (Integer) tValue.getValue() < Integer.parseInt(threshold);
            case gt:
                return tValue -> (Integer) tValue.getValue() > Integer.parseInt(threshold);
            default:
                throw new InvalidObjectiveError(Arrays.toString(Operator.values()) + "are the supported operators for Integer");
        }
    }

    private <T> Predicate<ServiceMetricValue<T>> doublePredicate(Operator operator, String threshold) {
        switch(operator) {
            case lt:
                return tValue -> (Double) tValue.getValue() < Double.parseDouble(threshold);
            case gt:
                return tValue -> (Double) tValue.getValue() > Double.parseDouble(threshold);
            default:
                throw new InvalidObjectiveError("`lt` and `gt` are the supported operators for Double");
        }
    }

    private <T> Predicate<ServiceMetricValue<T>> booleanPredicate(Operator operator, String threshold) {
        if (operator == Operator.eq) {
            return tValue -> tValue.getValue().equals(Boolean.valueOf(threshold));
        }
        throw new InvalidObjectiveError("`eq` is the only supported operators for Boolean");
    }

    private <T> Predicate<ServiceMetricValue<T>> stringPredicate(Operator operator, String threshold) {

        if (transform == null) {
            if (operator == Operator.eq) {
                return tValue -> tValue.getValue().equals(threshold);
            }
            throw new InvalidObjectiveError("`eq` is the only supported operators for String");
        }

        if (!transform.equals("length")) {
            throw new InvalidObjectiveError("`length` is the only supported transform for String");
        }

        switch(operator) {
            case eq:
                return tValue -> tValue.getValue().toString().length() == Integer.parseInt(threshold);

            case lt:
                return tValue -> tValue.getValue().toString().length() < Integer.parseInt(threshold);

            case gt:
                return tValue -> tValue.getValue().toString().length() > Integer.parseInt(threshold);

            default:
                throw new InvalidObjectiveError(Arrays.toString(Operator.values()) +
                    "are the supported operators for String length");
        }
    }

}
