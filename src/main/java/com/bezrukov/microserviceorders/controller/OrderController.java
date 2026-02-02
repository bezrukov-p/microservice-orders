package com.bezrukov.microserviceorders.controller;

import com.bezrukov.microserviceorders.dto.CreateOrderRequest;
import com.bezrukov.microserviceorders.dto.OrderDto;
import com.bezrukov.microserviceorders.dto.UpdateOrderRequest;
import com.bezrukov.microserviceorders.dto.UserDto;
import com.bezrukov.microserviceorders.entity.Order;
import com.bezrukov.microserviceorders.entity.Role;
import com.bezrukov.microserviceorders.entity.Status;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.exception.AccessDeniedException;
import com.bezrukov.microserviceorders.service.AuthService;
import com.bezrukov.microserviceorders.service.OrderService;
import com.bezrukov.microserviceorders.utils.MapperDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    private final OrderService orderService;
    private final AuthService authService;

    @Autowired
    public OrderController(OrderService orderService, AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        User user = authService.getCurrentUser();
        Order order = orderService.createOrder(createOrderRequest.description(), Status.CREATED, user);

        return ResponseEntity.ok(MapperDto.orderToDto(order));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders.stream().map(MapperDto::orderToDto).toList());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<OrderDto>> getOrders() {
        User user = authService.getCurrentUser();
        List<Order> orders = orderService.getOrders(user.getId());

        return ResponseEntity.ok(orders.stream().map(MapperDto::orderToDto).toList());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable UUID id, @RequestBody UpdateOrderRequest updateOrderRequest) {
        Order order = orderService.setStatus(id, Status.valueOf(updateOrderRequest.status()));

        return ResponseEntity.ok(MapperDto.orderToDto(order));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        User user = authService.getCurrentUser();
        if (Role.ROLE_USER.equals(user.getRole())) {
            Order order = orderService.getOrderById(id);
            if (!order.getUser().getId().equals(user.getId())) {
                throw new AccessDeniedException("You do not have permission to delete this order");
            }
        }

        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
