package com.moonkeyeu.core.api.settings;

import com.moonkeyeu.core.api.settings.exceptions.*;
import jakarta.mail.MessagingException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.moonkeyeu.core.api.settings.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ACCOUNT_LOCKED.getCode())
                        .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ACCOUNT_DISABLED.getCode())
                        .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BAD_CREDENTIALS.getCode())
                        .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(INVALID_PASSWORD.getCode())
                        .businessErrorDescription(INVALID_PASSWORD.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponse> handleException(ConflictException exp) {
        return ResponseEntity
                .status(CONFLICT)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ENTITY_CONFLICT.getCode())
                        .businessErrorDescription(ENTITY_CONFLICT.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ExceptionResponse> handleException(NumberFormatException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(INVALID_REQUEST_PARAM.getCode())
                        .businessErrorDescription(INVALID_REQUEST_PARAM.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ResourceNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ENTITY_NOT_FOUND.getCode())
                        .businessErrorDescription(ENTITY_NOT_FOUND.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(NoResourceFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ENTITY_NOT_FOUND.getCode())
                        .businessErrorDescription(ENTITY_NOT_FOUND.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<Map<String, Object>> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    Map<String, Object> map = Map.of(fieldName, errorMessage);
                    errors.add(map);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .validationErrors(errors)
                        .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(InvalidHeadersException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidHeadersException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(INVALID_HEADERS.getCode())
                        .businessErrorDescription(INVALID_HEADERS.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleException(AuthorizationDeniedException exp) {
        return ResponseEntity
                .status(FORBIDDEN)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ACCESS_DENIED.getCode())
                        .businessErrorDescription(ACCESS_DENIED.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidJwtTokenException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(INVALID_JWT_TOKEN.getCode())
                        .businessErrorDescription(INVALID_JWT_TOKEN.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(InvalidGoogleJwtToken.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidGoogleJwtToken exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(INVALID_GOOGLE_TOKEN.getCode())
                        .businessErrorDescription(INVALID_GOOGLE_TOKEN.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleException(SessionExpiredException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(EXPIRED_SESSION.getCode())
                        .businessErrorDescription(EXPIRED_SESSION.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(SessionNotFoundException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(SESSION_NOT_FOUND.getCode())
                        .businessErrorDescription(SESSION_NOT_FOUND.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(InvalidResetTokenException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidResetTokenException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(INVALID_RESET_TOKEN.getCode())
                        .businessErrorDescription(INVALID_RESET_TOKEN.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidOtpException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(INVALID_OTP_CODE.getCode())
                        .businessErrorDescription(INVALID_OTP_CODE.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(OtpLimitException.class)
    public ResponseEntity<ExceptionResponse> handleException(OtpLimitException exp) {
        return ResponseEntity
                .status(TOO_MANY_REQUESTS)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(OTP_RESEND_RATE_LIMIT.getCode())
                        .businessErrorDescription(OTP_RESEND_RATE_LIMIT.getDescription())
                        .delay(exp.getDelay())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<ExceptionResponse> handleException(RateLimitException exp) {
        return ResponseEntity
                .status(TOO_MANY_REQUESTS)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(RATE_LIMIT_EXCEED.getCode())
                        .businessErrorDescription(RATE_LIMIT_EXCEED.getDescription())
                        .delay(exp.getDelay())
                        .error(exp.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(RemoteServiceUnavailableException.class)
    public ResponseEntity<ExceptionResponse> handleException(RemoteServiceUnavailableException exp) {
        return ResponseEntity
                .status(SERVICE_UNAVAILABLE)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(UNAVAILABLE_REMOTE_API.getCode())
                        .businessErrorDescription(UNAVAILABLE_REMOTE_API.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ExceptionResponse> handleWebClientResponseException(WebClientResponseException exp) {
        return ResponseEntity
                .status(exp.getStatusCode())
                .body(ExceptionResponse.builder()
                        .businessErrorCode(ERROR_REMOTE_API.getCode())
                        .businessErrorDescription(ERROR_REMOTE_API.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<ExceptionResponse> handleWebClientRequestException(WebClientRequestException exp) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(UNAVAILABLE_REMOTE_API.getCode())
                        .businessErrorDescription(UNAVAILABLE_REMOTE_API.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .businessErrorDescription("Oops! somthing went wrong, try again later.")
                        .error(exp.getMessage())
                        .build()
                );
    }
}
