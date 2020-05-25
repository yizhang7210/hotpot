package com.hotpot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Builder
@Getter
@Setter
public class ServiceMetaData {
    private Tier tier;
    private Team owner;
    private Channel channel;
    private CodeRepository repository;
    private Team currentOnCallTeam;
    private Person currentOnCallPerson;
    private Version currentVersion;
    private Location selfLocation;
    private Location metricsLocation;
    private Location docsLocation;
    private Location logsLocation;

    public Optional<Tier> getTier() {
        return Optional.ofNullable(tier);
    }
    public Optional<Team> getOwner() {
        return Optional.ofNullable(owner);
    }
    public Optional<Channel> getChannel() {
        return Optional.ofNullable(channel);
    }
    public Optional<CodeRepository> getRepository() {
        return Optional.ofNullable(repository);
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

    public void mergeWith(ServiceMetaData another) {
        another.getTier().ifPresent(this::setTier);
        another.getOwner().ifPresent(this::setOwner);
        another.getChannel().ifPresent(this::setChannel);
        another.getRepository().ifPresent(this::setRepository);
        another.getCurrentOnCallTeam().ifPresent(this::setCurrentOnCallTeam);
        another.getCurrentOnCallPerson().ifPresent(this::setCurrentOnCallPerson);
        another.getCurrentVersion().ifPresent(this::setCurrentVersion);
        another.getSelfLocation().ifPresent(this::setSelfLocation);
        another.getMetricsLocation().ifPresent(this::setMetricsLocation);
        another.getDocsLocation().ifPresent(this::setDocsLocation);
        another.getLogsLocation().ifPresent(this::setLogsLocation);
    }

}
