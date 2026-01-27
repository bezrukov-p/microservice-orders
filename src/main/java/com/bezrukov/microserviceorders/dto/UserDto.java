package com.bezrukov.microserviceorders.dto;

import java.util.UUID;

public record UserDto(UUID id, String username, String role) {
}
