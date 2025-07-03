package com.moonkeyeu.etl.api.settings;

import com.moonkeyeu.etl.api.settings.exceptions.RateLimitExceededException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> httpResponse(String message, Exception ex, WebRequest request, HttpStatus status) {
        ErrorAttributes errorAttributes = new DefaultErrorAttributes();
        Map<String, Object> map = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        map.put("error", message + ": " + ex.getMessage());
        map.put("status", status);
        map.put("path", request.getDescription(false));
        return map;
    }

    @ExceptionHandler(value = RateLimitExceededException.class)
    public ResponseEntity<Map<String, Object>> handleRateLimitExceededException(RateLimitExceededException ex, WebRequest request) {
        HttpStatus status = HttpStatus.TOO_MANY_REQUESTS;
        Map<String, Object> map = httpResponse("Rate Limit Exceeded", ex, request, status);
        map.put("next_use_secs", ex.getNextUseSeconds());

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> map = httpResponse("Something Went Wrong", ex, request, status);
        return new ResponseEntity<>(map, status);
    }
}
