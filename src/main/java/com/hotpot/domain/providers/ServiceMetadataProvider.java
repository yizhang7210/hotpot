package com.hotpot.domain.providers;

import com.hotpot.domain.ServiceId;
import com.hotpot.domain.Service;

public interface ServiceMetadataProvider {

    Service fetchServiceById(ServiceId serviceId);

}
