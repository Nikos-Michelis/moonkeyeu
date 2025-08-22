package com.moonkeyeu.core.api.settings.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class NasaApodFetchException extends RuntimeException {
    private HttpStatusCode httpStatusCode;
    public NasaApodFetchException (String message, HttpStatusCode httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
    public NasaApodFetchException (String message) {
        super(message);
    }
}
