package com.bezrukov.microserviceorders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на аутентификацию (регистрация/вход)")
public record AuthRequest(

        @Schema(
                description = "Имя пользователя для входа в систему",
                example = "john_doe",
                required = true
        )
        String username,

        @Schema(
                description = "Пароль пользователя",
                required = true
        )
        String password
) {}
