package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.entity.Role;
import com.bezrukov.microserviceorders.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUser(String username);

    User getUser(UUID id);

    List<User> getAllUsers();

    User create(String username, String password, Role role);

    User delete(UUID id);
}
