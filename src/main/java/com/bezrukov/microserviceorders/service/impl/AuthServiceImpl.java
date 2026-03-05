package com.bezrukov.microserviceorders.service.impl;

import com.bezrukov.microserviceorders.dto.AuthRequest;
import com.bezrukov.microserviceorders.dto.AuthResponse;
import com.bezrukov.microserviceorders.dto.UserDto;
import com.bezrukov.microserviceorders.entity.RefreshToken;
import com.bezrukov.microserviceorders.entity.Role;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.exception.AuthenticationException;
import com.bezrukov.microserviceorders.exception.InvalidRefreshTokenException;
import com.bezrukov.microserviceorders.service.AuthService;
import com.bezrukov.microserviceorders.service.JwtService;
import com.bezrukov.microserviceorders.service.RefreshTokenService;
import com.bezrukov.microserviceorders.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public User register(AuthRequest authRequest) {
        return userService.create(authRequest.username(), authRequest.password(), Role.ROLE_USER);
    }

    public AuthResponse login(String userName, String password) {
        User user = authenticate(userName, password);

        return generateAuthResponse(user);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!refreshTokenService.validateRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        RefreshToken token =refreshTokenService.findByToken(refreshToken);
        User user = userService.getUser(token.getUserId());

        refreshTokenService.deleteByUserId(user.getId());

        return generateAuthResponse(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("Пользователь не аутентифицирован");
        }

        String username = authentication.getName();

        return userService.getUser(username);
    }

    private User authenticate(String userName, String password) {
        User user = userService.getUser(userName);
        if (user == null) {
            throw new AuthenticationException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                refreshToken.getExpiresAt(),
                new UserDto(user.getId(), user.getUsername(), user.getRole().name())
        );
    }
}
