package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.dto.AuthResponse;
import com.bezrukov.microserviceorders.dto.RegisterRequest;
import com.bezrukov.microserviceorders.dto.UserProfileResponse;
import com.bezrukov.microserviceorders.entity.RefreshToken;
import com.bezrukov.microserviceorders.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public User register(RegisterRequest registerRequest) {
        return userService.create(registerRequest.username(), registerRequest.password());
    }

    public AuthResponse login(String userName, String password) {
        User user = authenticate(userName, password);

        return generateAuthResponse(user);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!refreshTokenService.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        RefreshToken token =refreshTokenService.findByToken(refreshToken);
        User user = userService.getUser(token.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        } //TODO

        refreshTokenService.deleteByUserId(user.getId());

        return generateAuthResponse(user);
    }

    public UserProfileResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();

        return new UserProfileResponse(username, role);
    }

    private User authenticate(String userName, String password) {
        User user = userService.getUser(userName);
        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                user.getUsername(),
                user.getRole(),
                refreshToken.getExpiresAt()
        );
    }
}
