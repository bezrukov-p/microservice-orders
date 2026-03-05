package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.dto.AuthRequest;
import com.bezrukov.microserviceorders.dto.AuthResponse;
import com.bezrukov.microserviceorders.entity.User;

public interface AuthService {
    User getCurrentUser();

    User register(AuthRequest registerRequest);

    AuthResponse login(String username, String password);

    AuthResponse refreshToken(String s);
}
