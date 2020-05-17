package com.hotpot.domain.exceptions;

import java.util.HashMap;
import java.util.Map;

public class HotpotError extends RuntimeException {
    public HotpotError(String message) {
        super(message);
    }

    public Map<String, String> toDto() {
        return new HashMap<>() {{
            put("message", getMessage());
        }};
    }
}
