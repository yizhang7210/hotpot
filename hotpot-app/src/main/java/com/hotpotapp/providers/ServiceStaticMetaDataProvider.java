package com.hotpotapp.providers;


import com.hotpot.domain.Channel;
import com.hotpot.domain.CodeRepository;
import com.hotpot.domain.CodeRepositoryId;
import com.hotpot.domain.Location;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetaData;
import com.hotpot.domain.Team;
import com.hotpot.domain.TeamName;
import com.hotpot.domain.Version;
import com.hotpot.domain.providers.ServiceMetaDataProvider;
import com.hotpot.utils.Precedence;
import com.hotpotapp.domain.Tiers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ServiceStaticMetaDataProvider implements ServiceMetaDataProvider {

    private static final String REPOS = "https://repositories/";

    @Override
    public ServiceMetaData getMetaDataById(ServiceId serviceId) {
        String serviceName = serviceId.getValue();
        TeamName serviceTeam = TeamName.of(serviceName + "-team");

        if (serviceName.contains("empty")) {
            return ServiceMetaData.builder().build();
        }

        return ServiceMetaData.builder()
            .tier(getTier(serviceId).getTier())
            .owner(Team.of(serviceTeam))
            .channel(Channel.of(serviceName + "-channel"))
            .repository(CodeRepository.of(CodeRepositoryId.of(serviceName), Location.of(REPOS + serviceName)))
            .currentVersion(Version.of(serviceName + "-version"))
            .selfLocation(Location.of(serviceName + ".com"))
            .metricsLocation(Location.of(serviceName + ".com/metrics"))
            .docsLocation(Location.of(serviceName + ".com/docs"))
            .logsLocation(Location.of(serviceName + ".com/logs"))
            .build();
    }

    @Override
    public Precedence getPrecedence() {
        return Precedence.of(1);
    }

    private Tiers getTier(ServiceId serviceId) {
        switch (serviceId.getValue().length() % 3) {
            case 0:
                return Tiers.Tier_1;
            case 1:
                return Tiers.Tier_2;
            case 2:
                return Tiers.Tier_3;
        }
        return Tiers.Tier_3;
    }

}
