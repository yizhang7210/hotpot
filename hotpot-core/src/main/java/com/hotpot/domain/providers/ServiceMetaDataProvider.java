package com.hotpot.domain.providers;

import com.hotpot.domain.Precedence;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetaData;
import com.hotpot.domain.exceptions.HotpotInternalError;
import com.hotpot.domain.exceptions.HotpotUserError;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface ServiceMetaDataProvider {

    ServiceMetaData getMetaDataById(ServiceId serviceId);

    default Map<ServiceId, ServiceMetaData> getMetaDataByIds(Collection<ServiceId> serviceIds) {
        return serviceIds
            .stream()
            .collect(Collectors.toMap(Function.identity(), this::getMetaDataById));
    }

    Precedence getPrecedence();


    class ServiceMetaDataNotFoundError extends HotpotUserError {
        public ServiceMetaDataNotFoundError(ServiceId serviceId) {
            super(String.format("Service metadata cannot be found for service %s.", serviceId.getValue()));
        }
    }

    class ServiceMetaDataLoadError extends HotpotInternalError {
        public ServiceMetaDataLoadError(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
