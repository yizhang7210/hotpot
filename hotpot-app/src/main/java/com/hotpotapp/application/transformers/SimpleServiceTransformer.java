package com.hotpotapp.application.transformers;

import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.domain.Channel;
import com.hotpot.domain.Service;
import com.hotpot.domain.Tier;
import com.hotpot.domain.Version;
import com.hotpotapp.application.dtos.SimpleServiceDto;
import org.springframework.stereotype.Component;

@Component
public class SimpleServiceTransformer implements ServiceTransformer<SimpleServiceDto, Service> {

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
    public Service toDetailedDto(Service service) {
        return service;
    }
}
