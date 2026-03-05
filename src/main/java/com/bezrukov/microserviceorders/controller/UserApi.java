package com.bezrukov.microserviceorders.controller;

import com.bezrukov.microserviceorders.dto.ErrorResponse;
import com.bezrukov.microserviceorders.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Tag(name = "Пользователи", description = "API для управления пользователями (только для администраторов)")
@SecurityRequirement(name = "bearerAuth")
public interface UserApi {

    @Operation(
            summary = "Получение всех пользователей",
            description = "Возвращает список всех зарегистрированных пользователей. Доступно только для администраторов"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей получен",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    ResponseEntity<List<UserDto>> getAllUsers();

    @Operation(
            summary = "Удаление пользователя",
            description = "Удаляет пользователя из системы по его ID. Доступно только для администраторов"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> deleteUser(
            @Parameter(description = "UUID пользователя для удаления", required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id
    );
}
