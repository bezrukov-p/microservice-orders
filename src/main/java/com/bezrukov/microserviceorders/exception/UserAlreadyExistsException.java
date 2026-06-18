package com.bezrukov.microserviceorders.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException(String username) {
        super(String.format("User with username '%s' already exists", username), HttpStatus.CONFLICT);
    }
}
