package com.hotpotapp.domain;

import com.hotpot.domain.MetricId;
import com.hotpot.domain.Tier;
import lombok.Getter;

@Getter
public enum Metrics {
    NON_5XX_REQUEST_PERCENTAGE(MetricId.of("non-500-request-percentage")),
    KAFKA_MESSAGE_QUEUE_SIZE(MetricId.of("kafka-message-queue-size")),
    DOCUMENTATION_PRESENT(MetricId.of("documentation-present"));

    private final MetricId metricId;

    Metrics(MetricId metricId) {
        this.metricId = metricId;
    }
}
