package com.bezrukov.microserviceorders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Данные пользователя")
public record UserDto(

        @Schema(
                description = "Уникальный идентификатор пользователя",
                example = "123e4567-e89b-12d3-a456-426614174000",
                accessMode = Schema.AccessMode.READ_ONLY,
                required = true
        )
        UUID id,

        @Schema(
                description = "Имя пользователя",
                required = true
        )
        String username,

        @Schema(
                description = "Роль пользователя в системе",
                example = "ROLE_USER",
                required = true
        )
        String role
) {}
