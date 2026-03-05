package com.bezrukov.microserviceorders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на создание нового заказа")
public record CreateOrderRequest(

        @Schema(
                description = "Описание заказа",
                required = true
        )
        String description
) {}
