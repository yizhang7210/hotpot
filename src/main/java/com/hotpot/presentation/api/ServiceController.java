package com.hotpot.presentation.api;

import com.hotpot.application.transformers.ServiceTransformer;
import com.hotpot.application.usecases.ServiceUseCase;
import com.hotpot.domain.ServiceId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.function.Function;

@Controller
@AllArgsConstructor
public class ServiceController<T> {

    private final ServiceUseCase serviceUseCase;
    private final ServiceTransformer<T> serviceTransformer;

    @GetMapping("${hotpot.web-api.base-url}/services")
    public ResponseEntity<List<ServiceId>> getServiceIds() {
        return ResponseEntity.ok(serviceUseCase.getServiceIds(Function.identity()));
    }

    @GetMapping("${hotpot.web-api.base-url}/services/{serviceId}")
    public ResponseEntity<T> getServiceById(@PathVariable("serviceId") String serviceId) {
        return ResponseEntity.ok(serviceUseCase.getServiceById(ServiceId.of(serviceId), serviceTransformer::toDTO));
    }

}
