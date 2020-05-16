package com.hotpotapp.providers;


import com.hotpot.domain.Channel;
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

    @Override
    public ServiceMetaData getMetaDataById(ServiceId serviceId) {
        String serviceName = serviceId.getValue();
        return ServiceMetaData.builder()
            .channel(Channel.of(serviceName + "-channel"))
            .currentVersion(Version.of(serviceName + "-version"))
            .owner(Team.of(TeamName.of(serviceName + "-team")))
            .tier(Tiers.Tier_1.getTier())
            .selfLocation(Location.of(serviceName + ".com"))
            .docsLocation(Location.of(serviceName + ".com/docs"))
            .metricsLocation(Location.of(serviceName + ".com/metrics"))
            .build();
    }

    @Override
    public Precedence getPrecedence() {
        return Precedence.of(1);
    }

}
