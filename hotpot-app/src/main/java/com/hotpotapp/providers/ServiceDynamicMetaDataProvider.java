package com.hotpotapp.providers;


import com.hotpot.domain.Location;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetaData;
import com.hotpot.domain.Team;
import com.hotpot.domain.TeamName;
import com.hotpot.domain.Version;
import com.hotpot.domain.providers.ServiceMetaDataProvider;
import com.hotpot.utils.Precedence;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ServiceDynamicMetaDataProvider implements ServiceMetaDataProvider {

    @Override
    public ServiceMetaData getMetaDataById(ServiceId serviceId) {
        String serviceName = serviceId.getValue();
        return ServiceMetaData.builder()
            .selfLocation(Location.of(serviceName + ".com"))
            .currentVersion(Version.of(serviceName + "-version-deployed"))
            .currentOnCallTeam(Team.of(TeamName.of("another-team")))
            .build();
    }

    @Override
    public Precedence getPrecedence() {
        return Precedence.of(2);
    }

}
