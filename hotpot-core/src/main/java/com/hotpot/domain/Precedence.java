package com.hotpot.domain;

import com.hotpot.domain.exceptions.HotpotUserError;
import lombok.Getter;

@Getter
public class Precedence {

    private final Integer value;

    public static final Integer HIGHEST = 999;
    public static final Integer LOWEST = 0;

    private Precedence(Integer value) {
        this.value = value;
    }

    public static Precedence of(Integer value) {
        if (value >= LOWEST && value <= HIGHEST) {
            return new Precedence(value);
        }
        throw new PrecedenceOutOfRangeErrorHotpot(value);
    }

    static class PrecedenceOutOfRangeErrorHotpot extends HotpotUserError {
        public PrecedenceOutOfRangeErrorHotpot(int value) {
            super(String.format(
                "OverrideOrder out of range. It should be between %d and %d. Not %d.",
                LOWEST, HIGHEST, value)
            );
        }
    }

}
