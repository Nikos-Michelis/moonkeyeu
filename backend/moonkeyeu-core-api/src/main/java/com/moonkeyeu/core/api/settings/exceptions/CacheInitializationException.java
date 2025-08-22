package com.moonkeyeu.core.api.settings.exceptions;

public class CacheInitializationException extends RuntimeException {
    public CacheInitializationException(String message) { super(message); }
    public CacheInitializationException(String message, Throwable throwable) { super(message, throwable); }

}
