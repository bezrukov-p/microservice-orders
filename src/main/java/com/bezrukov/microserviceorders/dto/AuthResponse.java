package com.bezrukov.microserviceorders.dto;

import java.time.Instant;

public record AuthResponse(
        String token,
        String refreshToken,
        Instant expiresAt,
        UserDto user
) {
}
