package com.hotpotapp.providers;


import com.hotpot.domain.ServiceId;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class EmptyServiceIdentityProvider implements ServiceIdentityProvider {

    @Override
    public Collection<ServiceId> getServiceIds() {
        return List.of(
            ServiceId.of("first-service"),
            ServiceId.of("second-service")
        );
    }
}
