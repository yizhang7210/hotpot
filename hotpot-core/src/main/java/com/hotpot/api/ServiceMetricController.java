package com.hotpot.api;

import com.hotpot.application.transformers.ServiceMetricTransformer;
import com.hotpot.application.transformers.ServiceMetricValueTransformer;
import com.hotpot.application.usecases.ServiceMetricUseCase;
import com.hotpot.domain.MetricId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

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

    @GetMapping("${hotpot.web-api.base-url}/metrics/{metricId}/values")
    public ResponseEntity<Map<String, W>> getServiceMetricValuesById(@PathVariable("metricId") String metricId) {
        return ResponseEntity.ok(
            serviceMetricUseCase.getServiceMetricValuesById(MetricId.of(metricId), serviceMetricValueTransformer::toDto)
        );
    }



}
