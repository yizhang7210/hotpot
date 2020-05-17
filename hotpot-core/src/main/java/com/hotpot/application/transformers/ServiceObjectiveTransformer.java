package com.hotpot.application.transformers;

import com.hotpot.domain.ServiceObjective;


public interface ServiceObjectiveTransformer<U, V> {
    U toDto(ServiceObjective objective);
    V toDetailedDto(ServiceObjective objective);
}
