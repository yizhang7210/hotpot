package com.hotpot.domain;

import lombok.Builder;

import java.util.Collection;
import java.util.Optional;

@Builder
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

    public Optional<Tier> getTier() {
        return Optional.ofNullable(tier);
    }

    public Optional<Team> getOwner() {
        return Optional.ofNullable(owner);
    }
    public Optional<Channel> getChannel() {
        return Optional.ofNullable(channel);
    }
    public Optional<CodeRepository> getCodeRepository() {
        return Optional.ofNullable(repository);
    }
    public Collection<ServiceId> getDependencies() {
        return dependencies;
    }
    public Optional<Team> getCurrentOnCallTeam() {
        return Optional.ofNullable(currentOnCallTeam);
    }
    public Optional<Person> getCurrentOnCallPerson() {
        return Optional.ofNullable(currentOnCallPerson);
    }
    public Optional<Version> getCurrentVersion() {
        return Optional.ofNullable(currentVersion);
    }
    public Optional<Location> getSelfLocation() {
        return Optional.ofNullable(selfLocation);
    }
    public Optional<Location> getMetricsLocation() {
        return Optional.ofNullable(metricsLocation);
    }

    public Optional<Location> getDocsLocation() {
        return Optional.ofNullable(docsLocation);
    }

    public Optional<Location> getLogsLocation() {
        return Optional.ofNullable(logsLocation);
    }


}
