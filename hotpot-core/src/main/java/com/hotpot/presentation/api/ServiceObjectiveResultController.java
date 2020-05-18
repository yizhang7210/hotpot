package com.hotpot.presentation.api;

import com.hotpot.application.transformers.ServiceObjectiveResultTransformer;
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
public class ServiceObjectiveResultController<T> {

    private final ServiceObjectiveUseCase serviceObjectiveUseCase;
    private final ServiceObjectiveResultTransformer<T> serviceObjectiveResultTransformer;

    @GetMapping("${hotpot.web-api.base-url}/objectives/{objectiveId}/results")
    public ResponseEntity<List<T>> getObjectiveResultsById(@PathVariable("objectiveId") String objectiveId) {
        return ResponseEntity.ok(
            serviceObjectiveUseCase.getObjectiveResultsById(ObjectiveId.of(objectiveId), serviceObjectiveResultTransformer::toDto)
        );
    }

}
