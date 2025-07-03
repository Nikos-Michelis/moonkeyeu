package com.moonkeyeu.core.api.settings;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * 1XX for general errors.
 * 3XX for authentication errors.
 * 4XX for account-related errors.
 */
@Getter
public enum BusinessErrorCodes {
    NO_CODE(0, NOT_IMPLEMENTED, "No code"),

    INVALID_REQUEST_PARAM(101, BAD_REQUEST, "Invalid request parameter value."),
    ENTITY_CONFLICT(102, CONFLICT, "Entity has already been added to your list."),
    ENTITY_NOT_FOUND(103, NOT_FOUND, "Entity Not Found."),
    INVALID_HEADERS(104, BAD_REQUEST, "Entity Not Found."),

    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Current password is incorrect."),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "The new password does not match."),
    BAD_CREDENTIALS(302, UNAUTHORIZED, "Invalid credentials."),
    INVALID_JWT_TOKEN(303, UNAUTHORIZED, "Invalid Json Web Token (JWT)."),
    INVALID_OTP_CODE(304, UNAUTHORIZED, "Invalid One Time Password (OTP) code."),
    INVALID_RESET_TOKEN(305, UNAUTHORIZED, "Invalid reset token."),
    OTP_RESEND_RATE_LIMIT(306, TOO_MANY_REQUESTS, "You have exceed the maximum OTP resend times."),
    RATE_LIMIT_EXCEED(307, TOO_MANY_REQUESTS, "You have exceed the maximum number of allowed requests. Please try again later."),
    EXPIRED_SESSION(308, UNAUTHORIZED, "Your Session has expired."),
    SESSION_NOT_FOUND(309, UNAUTHORIZED, "No session found. Session might be expired or user not logged in."),
    INVALID_GOOGLE_TOKEN(310, UNAUTHORIZED, "Invalid Google Id token."),

    ACCOUNT_LOCKED(401, FORBIDDEN, "User account is locked."),
    ACCOUNT_DISABLED(402, FORBIDDEN, "User account is disabled."),
    INVALID_PASSWORD(403, UNAUTHORIZED, "Invalid username and / or password."),
    ACCESS_DENIED(404, FORBIDDEN, "You don't have the permission to perform this action."),
    ;

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus status, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = status;
    }
}