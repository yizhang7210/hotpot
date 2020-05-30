package com.hotpot.staticdata.yaml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hotpot.domain.MetricId;
import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceMetric;
import com.hotpot.domain.ServiceObjective;
import com.hotpot.domain.providers.ServiceMetricProvider;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class YamlServiceObjectiveProvider implements ServiceObjectiveProvider {

    private final String objectivesDefinitionFile;
    private final ResourceLoader resourceLoader;
    private final ServiceMetricProvider serviceMetricProvider;

    private Collection<ServiceObjective> cachedObjectives;

    @Override
    public Collection<ServiceObjective> getObjectives() {
        if (cachedObjectives != null) {
            return cachedObjectives;
        }

        Collection<ServiceObjective> objectives = loadObjectives();
        cachedObjectives = objectives;
        return objectives;
    }

    @Override
    public Optional<ServiceObjective> getObjectiveById(ObjectiveId objectiveId) {
        return getObjectives().stream()
            .filter(m -> m.getId().equals(objectiveId))
            .findFirst();
    }

    private Collection<ServiceObjective> loadObjectives() {
        Collection<ServiceMetric<?>> metrics = serviceMetricProvider.getAllMetrics();
        List<YamlServiceObjective> listedObjectives = getListedObjectives();

        validateObjectives(listedObjectives, metrics.stream().map(ServiceMetric::getId).collect(Collectors.toList()));

        return listedObjectives.stream()
            .map(o -> o.toServiceObjective(metrics))
            .collect(Collectors.toList());
    }

    private List<YamlServiceObjective> getListedObjectives() {
        try {
            InputStream objectives = resourceLoader.getResource(
                ResourceLoader.CLASSPATH_URL_PREFIX + objectivesDefinitionFile).getInputStream();

            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

            return objectMapper.readValue(objectives, new TypeReference<>() {
            });
        } catch (IOException e) {
            String message = String.format("Having trouble reading from the objectives file: %s", objectivesDefinitionFile);
            throw new ServiceObjectiveLoadError(message, e);
        }
    }

    private void validateObjectives(List<YamlServiceObjective> listedObjectives, Collection<MetricId> metricIds) {

        Collection<String> metricIdStrings = metricIds.stream().map(MetricId::getValue).collect(Collectors.toList());

        List<String> invalidObjectives = new ArrayList<>();

        for (YamlServiceObjective objective : listedObjectives) {
            List<String> listedMetricIds = objective.getCriteria().stream()
                .map(YamlCriterion::getMetricId).collect(Collectors.toList());

            if (!metricIdStrings.containsAll(listedMetricIds)) {
                invalidObjectives.add(objective.getId());
            }
        }

        if (invalidObjectives.size() > 0) {
            throw new InvalidObjectiveError(
                String.format("%s objectives contain metricIds that are not defined.", invalidObjectives)
            );
        }
    }

}
