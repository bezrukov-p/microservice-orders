package com.bezrukov.microserviceorders.service.impl;

import com.bezrukov.microserviceorders.entity.Order;
import com.bezrukov.microserviceorders.entity.Status;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.exception.OrderNotFoundException;
import com.bezrukov.microserviceorders.repository.OrderRepository;
import com.bezrukov.microserviceorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public Order createOrder(String description, Status status, User user) {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .status(status)
                .description(description)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return orderRepository.save(order);
    }

    public Order deleteOrder(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);
        return order;
    }

    public List<Order> getOrders(UUID userId) {
        return orderRepository.getOrdersByUser_Id(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order setStatus(UUID orderId, Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
