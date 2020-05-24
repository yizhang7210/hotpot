package com.hotpot.plugins;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hotpot.domain.MetricId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.providers.ServiceMetricProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class YamlServiceMetricProvider implements ServiceMetricProvider {

    private final String metricsDefinitionFile;
    private final ResourceLoader resourceLoader;

    private Collection<ServiceMetric<?>> cachedMetrics;

    @Override
    public Collection<ServiceMetric<?>> getAllMetrics() {
        if (cachedMetrics != null) {
            return cachedMetrics;
        }

        Collection<ServiceMetric<?>> metrics = loadMetrics();
        cachedMetrics = metrics;
        return metrics;
    }

    private Collection<ServiceMetric<?>> loadMetrics() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            File metricLocation = resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + metricsDefinitionFile).getFile();
            return objectMapper.readValue(metricLocation, new TypeReference<List<YamlServiceMetric>>(){})
                .stream()
                .map(YamlServiceMetric::toServiceMetric)
                .collect(Collectors.toList());
        } catch (IOException e) {
            String message = String.format("Having trouble reading from the metrics file: %s", metricsDefinitionFile);
            throw new ServiceMetricLoadError(message, e);
        }
    }

    @Override
    public ServiceMetric<?> getById(MetricId metricId) {
        return getAllMetrics().stream()
            .filter(m -> m.getId().equals(metricId))
            .findFirst()
            .orElseThrow(() -> new ServiceMetricNotFoundErrorHotpot(metricId));
    }

}
