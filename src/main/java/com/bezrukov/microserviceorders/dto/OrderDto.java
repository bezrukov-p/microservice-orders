package com.bezrukov.microserviceorders.dto;

import com.bezrukov.microserviceorders.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Данные заказа")
public record OrderDto(
        @Schema(
                description = "Уникальный идентификатор заказа",
                example = "123e4567-e89b-12d3-a456-426614174000",
                accessMode = Schema.AccessMode.READ_ONLY,
                required = true
        )
        UUID id,

        @Schema(
                description = "Описание заказа",
                required = true
        )
        String description,

        @Schema(
                description = "Текущий статус заказа",
                example = "CREATED",
                required = true
        )
        Status status,

        @Schema(
                description = "Дата и время создания заказа",
                example = "2024-01-15T10:30:00",
                required = true,
                accessMode = Schema.AccessMode.READ_ONLY
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Информация о пользователе, создавшем заказ",
                required = true
        )
        UserDto user
) {}
