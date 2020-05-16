package com.hotpotapp.domain;

import com.hotpot.domain.Tier;
import lombok.Getter;

@Getter
public enum Tiers {
    Tier_1(Tier.of("1", "The most important services")),
    Tier_2(Tier.of("2", "The somewhat important services")),
    Tier_3(Tier.of("3", "Who cares right?"));

    private final Tier tier;

    Tiers(Tier tier) {
        this.tier = tier;
    }
}
