package com.hotpot.domain.providers;

import com.hotpot.domain.ServiceId;

import java.util.Collection;

public interface ServiceIdentityProvider {

    Collection<ServiceId> getServiceIds();

}
