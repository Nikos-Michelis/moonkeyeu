package com.moonkeyeu.core.api.settings.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidResetTokenException extends RuntimeException {
    public InvalidResetTokenException(String message) {
        super(message);
    }
    public InvalidResetTokenException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
