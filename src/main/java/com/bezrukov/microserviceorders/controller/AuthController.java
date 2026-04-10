package com.bezrukov.microserviceorders.controller;

import com.bezrukov.microserviceorders.dto.AuthRequest;
import com.bezrukov.microserviceorders.dto.AuthResponse;
import com.bezrukov.microserviceorders.dto.RefreshTokenRequest;
import com.bezrukov.microserviceorders.dto.UserDto;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.service.AuthService;
import com.bezrukov.microserviceorders.utils.MapperDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST контроллер для управления аутентификацией пользователей.
 * Предоставляет endpoints для регистрации, входа в систему, обновления токенов
 * и получения информации о текущем пользователе.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param registerRequest данные для регистрации (имя пользователя и пароль)
     */
    @PostMapping("/register")
    @Override
    public ResponseEntity<UserDto> register(@RequestBody AuthRequest registerRequest) {
        log.info("Registering user: {}", registerRequest.username());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                MapperDto.userToDto(authService.register(registerRequest))
        );
    }

    /**
     * Аутентифицирует пользователя и возвращает JWT токены.
     *
     * @param authRequest учетные данные пользователя (имя пользователя и пароль)
     */
    @PostMapping("/login")
    @Override
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest.username(), authRequest.password()));
    }

    /**
     * Обновляет access токен с использованием refresh токена.
     *
     * @param refreshTokenRequest запрос, содержащий refresh токен
     * @return AuthResponse с новой парой токенов
     */
    @PostMapping("/refresh")
    @Override
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest.refreshToken()));
    }

    /**
     * Получает информацию о текущем аутентифицированном пользователе.
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/me")
    @Override
    public ResponseEntity<UserDto> me() {
        User user = authService.getCurrentUser();
        return ResponseEntity.ok(MapperDto.userToDto(user));
    }
}
