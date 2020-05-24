package com.hotpot.domain.exceptions;

public class HotpotInternalError extends HotpotError {
    public HotpotInternalError(String message, Throwable cause) {
        super(message, cause);
    }
}
