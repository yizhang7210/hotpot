package com.hotpotapp.application.transformers;

import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.domain.Channel;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceObjectiveEvaluator;
import com.hotpot.domain.Tier;
import com.hotpot.domain.Version;
import com.hotpot.domain.providers.ServiceObjectiveProvider;
import com.hotpotapp.application.dtos.ServiceWithResults;
import com.hotpotapp.application.dtos.SimpleServiceDto;
import com.hotpotapp.application.dtos.SimpleServiceObjectiveResultDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SimpleServiceTransformer implements ServiceTransformer<SimpleServiceDto, ServiceWithResults> {

    private final ServiceObjectiveProvider serviceObjectiveProvider;
    private final SimpleServiceObjectiveResultTransformer resultTransformer;
    private final ServiceObjectiveEvaluator serviceObjectiveEvaluator;

    @Override
    public SimpleServiceDto toDto(Service service) {
        return new SimpleServiceDto(
            service.getId().getValue(),
            service.getMetaData().getTier().map(Tier::getValue).orElse(null),
            service.getMetaData().getOwner().map(o -> o.getName().getValue()).orElse(null),
            service.getMetaData().getChannel().map(Channel::getValue).orElse(null),
            service.getMetaData().getCurrentVersion().map(Version::getValue).orElse(null)
        );
    }

    @Override
    public ServiceWithResults toDetailedDto(Service service) {

        Map<String, SimpleServiceObjectiveResultDto> results = serviceObjectiveProvider.getObjectives()
            .stream()
            .map(o -> serviceObjectiveEvaluator.runOnService(o, service.getId()))
            .collect(Collectors.toMap(sor -> sor.getObjectiveId().getValue(), resultTransformer::toDto));

        return new ServiceWithResults(service, results);
    }
}
