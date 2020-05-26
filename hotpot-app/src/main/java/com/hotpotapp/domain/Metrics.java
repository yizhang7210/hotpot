package com.hotpotapp.domain;

import com.hotpot.domain.MetricId;
import lombok.Getter;

@Getter
public enum Metrics {
    DOCUMENTATION_LOCATION(MetricId.of("doc-location")),
    AVERAGE_RELEASES_PER_DAY(MetricId.of("average-releases")),
    OVERALL_ROLLBACK_PERCENTAGE(MetricId.of("percentage-rollback"));

    private final MetricId metricId;

    Metrics(MetricId metricId) {
        this.metricId = metricId;
    }
}
