package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public User getUser(UUID id) {
        return userRepository.getUserById(id);
    }
}
