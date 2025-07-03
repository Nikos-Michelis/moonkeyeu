package com.moonkeyeu.core.api.settings.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SessionExpiredException extends RuntimeException{
    public SessionExpiredException(String message) {
        super(message);
    }
    public SessionExpiredException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
