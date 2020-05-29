package com.hotpot.api;

import com.hotpot.application.transformers.ServiceObjectiveResultTransformer;
import com.hotpot.application.transformers.ServiceObjectiveTransformer;
import com.hotpot.application.usecases.ServiceObjectiveUseCase;
import com.hotpot.domain.ObjectiveId;
import com.hotpot.domain.ServiceId;
import com.hotpot.utils.QueryUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ServiceObjectiveController<U, V, W> {

    private final ServiceObjectiveUseCase serviceObjectiveUseCase;
    private final ServiceObjectiveTransformer<U, V> serviceObjectiveTransformer;
    private final ServiceObjectiveResultTransformer<W> serviceObjectiveResultTransformer;

    @GetMapping("${hotpot.web-api.base-url}/objectives")
    public ResponseEntity<List<U>> getServiceObjectives() {
        return ResponseEntity.ok(serviceObjectiveUseCase.getServiceObjectives(serviceObjectiveTransformer::toDto));
    }

    @GetMapping("${hotpot.web-api.base-url}/objectives/{objectiveId}")
    public ResponseEntity<V> getServiceObjectiveById(@PathVariable("objectiveId") String objectiveId) {
        return ResponseEntity.ok(
            serviceObjectiveUseCase.getObjectiveById(ObjectiveId.of(objectiveId), serviceObjectiveTransformer::toDetailedDto)
        );
    }

    @GetMapping("${hotpot.web-api.base-url}/objectiveResults")
    public ResponseEntity<List<W>> getServiceObjectiveResults(@RequestParam(required = false) String q) {
        Map<String, String> filters = q == null ? Map.of() : QueryUtils.parseQuery(q);
        ObjectiveId objectiveId = filters.containsKey("objectiveId") ? ObjectiveId.of(filters.get("objectiveId")) : null;
        ServiceId serviceId = filters.containsKey("serviceId") ? ServiceId.of(filters.get("serviceId")) : null;

        return ResponseEntity.ok(
            serviceObjectiveUseCase.getServiceObjectiveResults(objectiveId, serviceId, serviceObjectiveResultTransformer::toDto)
        );
    }

}
