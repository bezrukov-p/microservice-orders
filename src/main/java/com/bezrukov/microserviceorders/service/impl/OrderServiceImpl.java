package com.bezrukov.microserviceorders.service.impl;

import com.bezrukov.microserviceorders.entity.Order;
import com.bezrukov.microserviceorders.entity.Status;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.exception.OrderNotFoundException;
import com.bezrukov.microserviceorders.repository.OrderRepository;
import com.bezrukov.microserviceorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(String description, Status status, User user) {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .status(status)
                .description(description)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        log.info("Order created: {}", order);

        return orderRepository.save(order);
    }

    @Override
    public Order deleteOrder(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            log.error("Order with id {} not found", id);
            return new OrderNotFoundException(id);
        });
        log.info("Order deleted: {}", order);
        orderRepository.delete(order);
        return order;
    }

    @Override
    public List<Order> getOrders(UUID userId) {
        return orderRepository.getOrdersByUser_Id(userId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order setStatus(UUID orderId, Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order with id {} not found", orderId);
            return new OrderNotFoundException(orderId);
        });
        order.setStatus(status);
        log.info("Order {} status updated to {}", orderId, status);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order with id {} not found", orderId);
            return new OrderNotFoundException(orderId);
        });
    }
}
