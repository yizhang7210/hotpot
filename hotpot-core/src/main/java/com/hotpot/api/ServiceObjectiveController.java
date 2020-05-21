package com.hotpot.api;

import com.hotpot.application.transformers.ServiceObjectiveResultTransformer;
import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.application.usecases.ServiceObjectiveUseCase;
import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ServiceObjectiveController<U, V, W> {

    private final ServiceObjectiveUseCase serviceObjectiveUseCase;
    private final ServiceObjectiveTransformer<U, V> serviceObjectiveTransformer;
    private final ServiceObjectiveResultTransformer<W> serviceObjectiveResultTransformer;

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

    @GetMapping("${hotpot.web-api.base-url}/objectives/{objectiveId}/results")
    public ResponseEntity<Map<String, W>> getObjectiveResultsById(@PathVariable("objectiveId") String objectiveId) {
        return ResponseEntity.ok(
            serviceObjectiveUseCase.getObjectiveResultsById(ObjectiveId.of(objectiveId), serviceObjectiveResultTransformer::toDto)
        );
    }

}
