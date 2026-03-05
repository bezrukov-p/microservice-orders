package com.bezrukov.microserviceorders.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ApiException {
    public AccessDeniedException() {
        super("Access denied", HttpStatus.FORBIDDEN);
    }

    public AccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
