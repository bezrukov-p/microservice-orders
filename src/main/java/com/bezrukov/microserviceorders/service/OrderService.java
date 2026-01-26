package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.entity.Order;
import com.bezrukov.microserviceorders.entity.Status;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

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

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByUser(UUID userId) {
        return orderRepository.getOrdersByUser_Id(userId);
    }

    public List<Order> getAllOrders(Status status) {
        return orderRepository.findAll();
    }

    public Order setStatus(UUID orderId, Status status) {
        Order order = orderRepository.getReferenceById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
