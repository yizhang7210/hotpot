package com.hotpot.lib.yaml;

import com.hotpot.domain.Channel;
import com.hotpot.domain.CodeRepository;
import com.hotpot.domain.CodeRepositoryId;
import com.hotpot.domain.Location;
import com.hotpot.domain.Service;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetaData;
import com.hotpot.domain.Team;
import com.hotpot.domain.TeamName;
import com.hotpot.domain.Tier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YamlService {

    private List<YamlTier> tiers;
    private List<YamlServiceData> services;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class YamlTier {
        private String name;
        private String description;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class YamlRepository {
        private String name;
        private String location;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class YamlServiceData {
        private String id;
        private String tier;
        private String owner;
        private String channel;
        private YamlRepository repository;
        private String selfLocation;
        private String metricsLocation;
        private String docsLocation;
        private String logsLocation;

        public Service toService(List<YamlTier> tiers) {
            YamlTier tierObject = tiers
                .stream()
                .filter(t -> t.name.equals(tier))
                .findFirst()
                .orElse(null);

            return new Service(
                ServiceId.of(id),
                ServiceMetaData.builder()
                    .tier(tierObject == null ? null : Tier.of(tierObject.name, tierObject.description))
                    .owner(Team.of(TeamName.of(owner)))
                    .channel(Channel.of(channel))
                    .repository(repository == null ? null :
                        CodeRepository.of(CodeRepositoryId.of(repository.name), Location.of(repository.location)))
                    .selfLocation(Location.of(selfLocation))
                    .metricsLocation(Location.of(metricsLocation))
                    .docsLocation(Location.of(docsLocation))
                    .logsLocation(Location.of(logsLocation))
                    .build()
            );
        }

    }

    List<Service> toServices() {
        return services.stream()
            .map(s -> s.toService(tiers))
            .collect(Collectors.toList());
    }
}
