package com.hotpot.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
public class ServiceMetaData {
    private final Tier tier;
    private final Team owner;
    private final Channel channel;
    private final CodeRepository repository;
    private final Collection<ServiceId> dependencies;
    private final Team currentOnCallTeam;
    private final Person currentOnCallPerson;
    private final Version currentVersion;
    private final Location selfLocation;
    private final Location metricsLocation;
    private final Location docsLocation;
    private final Location logsLocation;
}
