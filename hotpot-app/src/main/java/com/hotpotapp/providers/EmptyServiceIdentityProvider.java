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
            ServiceId.of("second-service"),
            ServiceId.of("third-service"),
            ServiceId.of("fourth-service"),
            ServiceId.of("fifth-service"),
            ServiceId.of("fifth-service-frontend"),
            ServiceId.of("sixth-service"),
            ServiceId.of("seventh-service"),
            ServiceId.of("best-service"),
            ServiceId.of("worst-service"),
            ServiceId.of("almost-not-a-service")
        );
    }
}
