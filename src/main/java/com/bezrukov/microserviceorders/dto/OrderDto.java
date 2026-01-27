package com.bezrukov.microserviceorders.dto;

import com.bezrukov.microserviceorders.entity.Status;

import java.time.LocalDateTime;

public record OrderDto(String description, Status status, LocalDateTime createdAt) {
}
