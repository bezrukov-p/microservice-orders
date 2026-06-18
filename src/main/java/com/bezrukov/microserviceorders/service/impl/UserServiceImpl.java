package com.bezrukov.microserviceorders.service.impl;

import com.bezrukov.microserviceorders.entity.Role;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.exception.UserAlreadyExistsException;
import com.bezrukov.microserviceorders.exception.UserNotFoundException;
import com.bezrukov.microserviceorders.repository.UserRepository;
import com.bezrukov.microserviceorders.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User create(String username, String password, Role role) {
        if (userRepository.existsByUsername(username)) {
            log.error("Username {} already exists", username);
            throw new UserAlreadyExistsException(username);
        }
        log.info("Creating user {}", username);
        return userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build()
        );
    }

    @Override
    public User delete(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User with id {} not found", id);
            return new UserNotFoundException(id);
        });
        log.info("Deleting user {}", id);
        userRepository.delete(user);
        return user;
    }

    @Override
    public User getUser(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            log.error("User with name {} not found", username);
            throw new UserNotFoundException(username);
        }

        return user;
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.error("User with id {} not found", id);
            return new UserNotFoundException(id);
        });
    }
}
