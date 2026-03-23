package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.entity.Order;
import com.bezrukov.microserviceorders.entity.Status;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.exception.OrderNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления заказами.
 */
public interface OrderService {
    /**
     * Создать заказ.
     *
     * @param description описание заказа
     * @param status      начальный статус
     * @param user        владелец заказа
     * @return созданный заказ
     */
    Order createOrder(String description, Status status, User user);

    /**
     * Удалить заказ.
     *
     * @param id идентификатор заказа
     * @return удаленный заказ
     * @throws OrderNotFoundException если заказ не найден
     */
    Order deleteOrder(UUID id);

    /**
     * Получить заказы пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список заказов пользователя
     */
    List<Order> getOrders(UUID userId);

    /**
     * Получить все заказы.
     *
     * @return список всех заказов
     */
    List<Order> getAllOrders();

    /**
     * Изменить статус заказа.
     *
     * @param orderId идентификатор заказа
     * @param status новый статус
     * @return обновленный заказ
     * @throws OrderNotFoundException если заказ не найден
     */
    Order setStatus(UUID orderId, Status status);

    /**
     * Получить заказ по идентификатору.
     *
     * @param id идентификатор заказа
     * @return заказ
     * @throws OrderNotFoundException если заказ не найден
     */
    Order getOrderById(UUID id);
}
