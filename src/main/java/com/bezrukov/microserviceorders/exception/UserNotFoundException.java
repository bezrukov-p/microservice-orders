package com.bezrukov.microserviceorders.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(UUID userId) {
        super(String.format("User with id '%s' not found", userId), HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(String username) {
        super(String.format("User with username '%s' not found", username), HttpStatus.NOT_FOUND);
    }
}
