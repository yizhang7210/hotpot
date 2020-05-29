package com.hotpot.api;

import com.hotpot.application.transformers.ServiceMetricTransformer;
import com.hotpot.application.transformers.ServiceMetricValueTransformer;
import com.hotpot.application.usecases.ServiceMetricUseCase;
import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceId;
import com.hotpot.utils.QueryUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@AllArgsConstructor
public class ServiceMetricController<U, V, W> {

    private final ServiceMetricUseCase serviceMetricUseCase;
    private final ServiceMetricTransformer<U, V> serviceMetricTransformer;
    private final ServiceMetricValueTransformer<W> serviceMetricValueTransformer;

    @GetMapping("${hotpot.web-api.base-url}/metrics")
    public ResponseEntity<List<U>> getServiceMetrics() {
        return ResponseEntity.ok(serviceMetricUseCase.getServiceMetrics(serviceMetricTransformer::toDto));
    }

    @GetMapping("${hotpot.web-api.base-url}/metrics/{metricId}")
    public ResponseEntity<V> getServiceMetricById(@PathVariable("metricId") String metricId) {
        return ResponseEntity.ok(
            serviceMetricUseCase.getServiceMetricById(MetricId.of(metricId), serviceMetricTransformer::toDetailedDto)
        );
    }

    @GetMapping("${hotpot.web-api.base-url}/metricValues")
    public ResponseEntity<List<W>> getServiceMetricValues(@RequestParam(required = false) String q) {
        Map<String, String> filters = q == null ? Map.of() : QueryUtils.parseQuery(q);
        MetricId metricId = filters.containsKey("metricId") ? MetricId.of(filters.get("metricId")) : null;
        ServiceId serviceId = filters.containsKey("serviceId") ? ServiceId.of(filters.get("serviceId")) : null;

        return ResponseEntity.ok(
            serviceMetricUseCase.getServiceMetricValues(metricId, serviceId, serviceMetricValueTransformer::toDto)
        );
    }

}
