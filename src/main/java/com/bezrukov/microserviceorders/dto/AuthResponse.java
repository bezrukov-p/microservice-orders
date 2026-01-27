package com.bezrukov.microserviceorders.dto;

import com.bezrukov.microserviceorders.entity.Role;

import java.time.Instant;

public record AuthResponse(
        String token,
        String refreshToken,
        String username,
        Role role,
        Instant expiresAt
) {
}
