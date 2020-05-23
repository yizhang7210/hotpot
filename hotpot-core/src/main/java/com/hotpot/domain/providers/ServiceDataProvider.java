package com.hotpot.domain.providers;

import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceMetricValue;

public interface ServiceDataProvider {

    boolean doesProvideFor(ServiceMetric metric);

    <T> ServiceMetricValue<T> getForService(ServiceMetric metric, ServiceId serviceId);
}
