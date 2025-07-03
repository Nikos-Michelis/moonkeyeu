package com.moonkeyeu.core.api.settings.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitException extends RuntimeException{
    private final Long delay;
    public RateLimitException(String message) {
        super(message);
        this.delay = null;
    }
    public RateLimitException(String message, Long delay) {
        super(message);
        this.delay = delay;
    }
}
