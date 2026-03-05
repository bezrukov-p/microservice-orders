package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.entity.Order;
import com.bezrukov.microserviceorders.entity.Status;
import com.bezrukov.microserviceorders.entity.User;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(String description, Status status, User user);

    Order deleteOrder(UUID id);

    List<Order> getOrders(UUID userId);

    List<Order> getAllOrders();

    Order setStatus(UUID orderId, Status status);

    Order getOrderById(UUID id);
}
