package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.entity.Role;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.repository.OrderRepository;
import com.bezrukov.microserviceorders.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User create(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        return userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build()
        );
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public User getUser(UUID id) {
        return userRepository.getUserById(id);
    }

    public User getUser(String username) {
        return userRepository.getUserByUsername(username);
    }
}
