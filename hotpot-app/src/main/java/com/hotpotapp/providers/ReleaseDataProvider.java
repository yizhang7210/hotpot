package com.hotpotapp.providers;


import com.hotpot.domain.DataPoint;
import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class ReleaseDataProvider {

    public Collection<DataPoint<Long>> getReleasesPerDay(ServiceId serviceId, LocalDate from, LocalDate to) {
        List<DataPoint<Long>> releases = new ArrayList<>();
        LocalDate date = from;
        while (date.isBefore(to)) {
            releases.add(new DataPoint<>(
                serviceId,
                MetricId.of("number-of-releases"),
                date.atStartOfDay().toInstant(ZoneOffset.UTC),
                Duration.ofDays(1),
                Math.round(Math.random() * 20) + 5
            ));
            date = date.plusDays(1);
        }

        return releases;
    }

    public Collection<DataPoint<Long>> getRollbacksPerDay(ServiceId serviceId, LocalDate from, LocalDate to) {
        List<DataPoint<Long>> metrics = new ArrayList<>();
        LocalDate date = from;
        while (date.isBefore(to)) {
            metrics.add(new DataPoint<>(
                serviceId,
                MetricId.of("rollbacks"),
                date.atStartOfDay().toInstant(ZoneOffset.UTC),
                Duration.ofDays(1),
                Math.round(Math.random() * 5)
            ));
            date = date.plusDays(1);
        }

        return metrics;
    }

}
