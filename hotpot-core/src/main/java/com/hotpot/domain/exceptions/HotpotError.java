package com.hotpot.domain.exceptions;

import java.util.HashMap;
import java.util.Map;

public class HotpotError extends RuntimeException {
    public HotpotError(String message) {
        super(message);
    }

    public HotpotError(String message, Throwable cause) {
        super(message, cause);
    }

    public Map<String, String> toDto() {
        return new HashMap<>() {{
            put("message", getMessage());
        }};
    }
}
