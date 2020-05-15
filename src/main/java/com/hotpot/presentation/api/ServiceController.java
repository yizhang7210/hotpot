package com.hotpot.presentation.api;

import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.providers.ServiceMetadataProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@Controller
public class ServiceController {

    private final ServiceMetadataProvider serviceMetadataProvider;

    @GetMapping("/services/{serviceId}")
    public ResponseEntity<Service> getServiceById(@PathVariable("serviceId") String serviceId) {
        Service service = serviceMetadataProvider.fetchServiceById(ServiceId.of(serviceId));
        return ResponseEntity.ok(service);
    }

}
