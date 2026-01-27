package com.bezrukov.microserviceorders.controller;

import com.bezrukov.microserviceorders.dto.AuthRequest;
import com.bezrukov.microserviceorders.dto.RefreshTokenRequest;
import com.bezrukov.microserviceorders.dto.RegisterRequest;
import com.bezrukov.microserviceorders.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest.username(), authRequest.password()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest.refreshToken()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
