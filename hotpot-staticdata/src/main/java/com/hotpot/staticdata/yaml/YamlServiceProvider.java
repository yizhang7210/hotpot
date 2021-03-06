package com.hotpot.staticdata.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hotpot.domain.Precedence;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetaData;
import com.hotpot.domain.providers.ServiceIdentityProvider;
import com.hotpot.domain.providers.ServiceMetaDataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class YamlServiceProvider implements ServiceIdentityProvider, ServiceMetaDataProvider {

    private final String servicesDefinitionFile;
    private final ResourceLoader resourceLoader;
    private final String precedence;

    private Collection<Service> cachedServices;

    @Override
    public Collection<ServiceId> getServiceIds() {
        if (cachedServices == null) {
            cachedServices = loadServices();
        }

        return cachedServices.stream().map(Service::getId).collect(Collectors.toList());
    }

    @Override
    public ServiceMetaData getMetaDataById(ServiceId serviceId) {
        if (cachedServices == null) {
            cachedServices = loadServices();
        }

        return cachedServices.stream()
            .filter(s -> s.getId().equals(serviceId))
            .findFirst()
            .orElseThrow(() -> new ServiceMetaDataNotFoundError(serviceId))
            .getMetaData();
    }

    @Override
    public Precedence getPrecedence() {
        return Precedence.of(Integer.parseInt(precedence));
    }

    private Collection<Service> loadServices() {

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            InputStream services = resourceLoader.getResource(
                ResourceLoader.CLASSPATH_URL_PREFIX + servicesDefinitionFile
            ).getInputStream();
            return objectMapper.readValue(services, YamlService.class).toServices();
        } catch (IOException e) {
            String message = String.format("Having trouble reading from the services file: %s", servicesDefinitionFile);
            throw new ServiceMetaDataLoadError(message, e);
        }
    }

}
