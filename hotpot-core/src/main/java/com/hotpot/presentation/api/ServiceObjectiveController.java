package com.hotpot.presentation.api;

import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.application.usecases.ServiceObjectiveUseCase;
import com.hotpot.domain.ObjectiveId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class ServiceObjectiveController<U, V> {

    private final ServiceObjectiveUseCase serviceObjectiveUseCase;
    private final ServiceObjectiveTransformer<U, V> serviceObjectiveTransformer;

    @GetMapping("${hotpot.web-api.base-url}/objectives")
    public ResponseEntity<List<U>> getObjectives() {
        return ResponseEntity.ok(serviceObjectiveUseCase.getServiceObjectives(serviceObjectiveTransformer::toDto));
    }

    @GetMapping("${hotpot.web-api.base-url}/objectives/{objectiveId}")
    public ResponseEntity<V> getObjectiveById(@PathVariable("objectiveId") String objectiveId) {
        return ResponseEntity.ok(
            serviceObjectiveUseCase.getObjectiveById(ObjectiveId.of(objectiveId), serviceObjectiveTransformer::toDetailedDto)
        );
    }

}
