package com.bezrukov.microserviceorders.service.impl;

import com.bezrukov.microserviceorders.entity.Role;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.exception.UserAlreadyExistsException;
import com.bezrukov.microserviceorders.exception.UserNotFoundException;
import com.bezrukov.microserviceorders.repository.UserRepository;
import com.bezrukov.microserviceorders.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User create(String username, String password, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        return userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build()
        );
    }

    public User delete(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
        return user;
    }

    public User getUser(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        return user;
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
