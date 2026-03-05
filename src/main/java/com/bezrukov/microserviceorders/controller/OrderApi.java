package com.bezrukov.microserviceorders.controller;

import com.bezrukov.microserviceorders.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Заказы", description = "API для управления заказами")
@SecurityRequirement(name = "bearerAuth")
public interface OrderApi {

    @Operation(
            summary = "Создание нового заказа",
            description = "Создает новый заказ для текущего аутентифицированного пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ успешно создан",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные заказа",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    ResponseEntity<OrderDto> createOrder(
            @Parameter(description = "Данные для создания заказа", required = true,
                    schema = @Schema(implementation = CreateOrderRequest.class))
            @RequestBody CreateOrderRequest createOrderRequest
    );

    @Operation(
            summary = "Получение всех заказов (для администраторов)",
            description = "Возвращает список всех заказов в системе. Доступно только для администраторов"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов получен",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован ",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/all")
    ResponseEntity<List<OrderDto>> getAllOrders();

    @Operation(
            summary = "Получение заказов текущего пользователя",
            description = "Возвращает список заказов, принадлежащих текущему аутентифицированному пользователю"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов пользователя получен",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован ",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    ResponseEntity<List<OrderDto>> getOrders();

    @Operation(
            summary = "Обновление статуса заказа",
            description = "Изменяет статус существующего заказа. Доступно только для администраторов"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус заказа успешно обновлен",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректный статус или ID заказа",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав (требуется роль ADMIN)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Заказ не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    ResponseEntity<OrderDto> updateOrder(
            @Parameter(description = "UUID заказа", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id,

            @Parameter(description = "Новые данные заказа", required = true,
                    schema = @Schema(implementation = UpdateOrderRequest.class))
            @RequestBody UpdateOrderRequest updateOrderRequest
    );

    @Operation(
            summary = "Удаление заказа",
            description = "Удаляет заказ. Пользователи могут удалять только свои заказы, администраторы - любые"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ успешно удален",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав для удаления этого заказа",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Заказ не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{orderId}")
    ResponseEntity<OrderDto> deleteOrder(
            @Parameter(description = "UUID заказа для удаления", required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID orderId
    );
}
