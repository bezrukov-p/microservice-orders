package com.bezrukov.microserviceorders.controller;

import com.bezrukov.microserviceorders.dto.*;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.service.AuthService;
import com.bezrukov.microserviceorders.service.impl.AuthServiceImpl;
import com.bezrukov.microserviceorders.utils.MapperDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody AuthRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                MapperDto.userToDto(authService.register(registerRequest))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest.username(), authRequest.password()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest.refreshToken()));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        User user = authService.getCurrentUser();
        return ResponseEntity.ok(MapperDto.userToDto(user));
    }
}
