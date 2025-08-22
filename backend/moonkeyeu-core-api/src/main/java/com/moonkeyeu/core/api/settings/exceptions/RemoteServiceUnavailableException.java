package com.moonkeyeu.core.api.settings.exceptions;

public class RemoteServiceUnavailableException extends RuntimeException{
    public RemoteServiceUnavailableException(String message) {
        super(message);
    }
    public RemoteServiceUnavailableException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
