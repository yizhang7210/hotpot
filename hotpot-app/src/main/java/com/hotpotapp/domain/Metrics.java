package com.hotpotapp.domain;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.Tier;
import lombok.Getter;

@Getter
public enum Metrics {
    DOCUMENTATION_PRESENT(MetricId.of("documentation-present")),
    AVERAGE_RELEASES_PER_DAY(MetricId.of("average-releases-per-day")),
    OVERALL_ROLLBACK_PERCENTAGE(MetricId.of("rollbacks-percentage"));

    private final MetricId metricId;

    Metrics(MetricId metricId) {
        this.metricId = metricId;
    }
}
