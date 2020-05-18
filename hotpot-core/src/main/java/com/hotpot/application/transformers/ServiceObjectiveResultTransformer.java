package com.hotpot.application.transformers;

import com.hotpot.domain.ServiceObjectiveResult;


public interface ServiceObjectiveResultTransformer<T> {
    T toDto(ServiceObjectiveResult result);
}
