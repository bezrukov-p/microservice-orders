package com.bezrukov.microserviceorders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на обновление статуса заказа")
public record UpdateOrderRequest(

        @Schema(
                description = "Новый статус заказа",
                example = "COMPLETED",
                required = true
        )
        String status
) {}
