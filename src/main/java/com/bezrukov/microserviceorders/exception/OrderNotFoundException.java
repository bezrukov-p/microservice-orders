package com.bezrukov.microserviceorders.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class OrderNotFoundException extends ApiException {
    public OrderNotFoundException(UUID orderId) {
        super(String.format("Order with id '%s' not found", orderId), HttpStatus.NOT_FOUND);
    }
}
