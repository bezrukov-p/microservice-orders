package com.bezrukov.microserviceorders.controller;

import com.bezrukov.microserviceorders.dto.CreateOrderRequest;
import com.bezrukov.microserviceorders.dto.OrderDto;
import com.bezrukov.microserviceorders.dto.UpdateOrderRequest;
import com.bezrukov.microserviceorders.entity.Order;
import com.bezrukov.microserviceorders.entity.Role;
import com.bezrukov.microserviceorders.entity.Status;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.service.AuthService;
import com.bezrukov.microserviceorders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/api/orders")
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
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        User user = authService.getCurrentUser();
        Order order = orderService.createOrder(createOrderRequest.description(), Status.CREATED, user);

        return ResponseEntity.ok(
                new OrderDto(order.getDescription(), order.getStatus(), order.getCreatedAt())
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders() {
        User user = authService.getCurrentUser();
        List<Order> orders = orderService.getAllOrders(user.getId());

        return ResponseEntity.ok(orders.stream().map(
                order -> new OrderDto(order.getDescription(), order.getStatus(), order.getCreatedAt())
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOrder(@PathVariable UUID id, @RequestBody UpdateOrderRequest updateOrderRequest) {
        Order order = orderService.setStatus(id, Status.valueOf(updateOrderRequest.status()));

        return ResponseEntity.ok(new OrderDto(order.getDescription(), order.getStatus(), order.getCreatedAt()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        User user = authService.getCurrentUser();
        if (Role.ROLE_USER.equals(user.getRole())) {
            Order order = orderService.getOrderById(id);
            if (!order.getUser().getId().equals(user.getId())) {
                return ResponseEntity.badRequest().build();
            }
        }

        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
