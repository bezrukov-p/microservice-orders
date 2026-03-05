package com.bezrukov.microserviceorders.utils;

import com.bezrukov.microserviceorders.dto.OrderDto;
import com.bezrukov.microserviceorders.dto.UserDto;
import com.bezrukov.microserviceorders.entity.Order;
import com.bezrukov.microserviceorders.entity.User;

public class MapperDto {
    public static UserDto userToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRole().name());
    }

    public static OrderDto orderToDto(Order order) {
        return new OrderDto(order.getId(), order.getDescription(), order.getStatus(), order.getCreatedAt(), userToDto(order.getUser()));
    }
}
