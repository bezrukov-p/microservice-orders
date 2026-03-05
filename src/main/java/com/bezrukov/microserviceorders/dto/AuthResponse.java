package com.bezrukov.microserviceorders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Ответ с данными аутентификации")
public record AuthResponse(

        @Schema(
                description = "JWT токен доступа",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ",
                required = true
        )
        String token,

        @Schema(
                description = "Refresh токен для обновления доступа",
                example = "d7f8a9b0-c1d2-4e5f-8a9b-0c1d2e3f4a5b",
                required = true
        )
        String refreshToken,

        @Schema(
                description = "Дата и время истечения токена доступа",
                example = "2024-01-15T11:30:00Z",
                required = true,
                pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        )
        Instant expiresAt,

        @Schema(
                description = "Информация о пользователе",
                required = true
        )
        UserDto user
) {}
