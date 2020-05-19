package com.hotpotapp.providers;


import com.hotpot.domain.Location;
import com.hotpot.domain.Person;
import com.hotpot.domain.ServiceId;
import com.hotpot.domain.ServiceMetaData;
import com.hotpot.domain.Team;
import com.hotpot.domain.TeamName;
import com.hotpot.domain.Version;
import com.hotpot.domain.providers.ServiceMetaDataProvider;
import com.hotpot.domain.Precedence;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ServiceDynamicMetaDataProvider implements ServiceMetaDataProvider {

    private static final Team ON_CALL_TEAM = Team.of(TeamName.of("The on-call team"));

    @Override
    public ServiceMetaData getMetaDataById(ServiceId serviceId) {
        String serviceName = serviceId.getValue();
        TeamName serviceTeam = TeamName.of(serviceName + "-team");

        if (serviceName.contains("empty")) {
            return ServiceMetaData.builder().build();
        }

        return ServiceMetaData.builder()
            .selfLocation(Location.of(serviceName + ".com"))
            .currentVersion(Version.of(serviceName + "-version-deployed"))
            .currentOnCallTeam(ON_CALL_TEAM)
            .currentOnCallPerson(Person.of("John Doe", serviceTeam))
            .build();
    }

    @Override
    public Precedence getPrecedence() {
        return Precedence.of(2);
    }

}
