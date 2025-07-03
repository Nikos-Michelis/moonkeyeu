package com.moonkeyeu.core.api.settings.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
    public InvalidJwtTokenException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
