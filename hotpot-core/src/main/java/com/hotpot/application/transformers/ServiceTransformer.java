package com.hotpot.application.transformers;

import com.hotpot.domain.Service;


public interface ServiceTransformer<U, V> {
    U toDto(Service service);
    V toDetailedDto(Service service);
}
