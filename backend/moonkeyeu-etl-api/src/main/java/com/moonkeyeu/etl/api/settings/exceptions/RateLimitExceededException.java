package com.moonkeyeu.etl.api.settings.exceptions;


import lombok.Getter;

@Getter
public class RateLimitExceededException extends RuntimeException {
    private long nextUseSeconds;

    public RateLimitExceededException(String message, long nextUseSeconds) {
        super(message);
        this.nextUseSeconds = nextUseSeconds;
    }
    public RateLimitExceededException(String message) {
        super(message);
    }

}
