package com.hotpot.application.transformers;

import com.hotpot.domain.Service;


public interface ServiceTransformer<T> {
    T toDTO(Service service);
}
