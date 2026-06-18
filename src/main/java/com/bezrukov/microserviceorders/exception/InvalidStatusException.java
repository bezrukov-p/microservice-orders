package com.bezrukov.microserviceorders.exception;

import org.springframework.http.HttpStatus;

public class InvalidStatusException extends ApiException {
    public InvalidStatusException(String status) {
        super(String.format("Invalid status: '%s'", status), HttpStatus.BAD_REQUEST);
    }
}
