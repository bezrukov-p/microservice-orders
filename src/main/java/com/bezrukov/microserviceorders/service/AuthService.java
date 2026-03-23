package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.dto.AuthRequest;
import com.bezrukov.microserviceorders.dto.AuthResponse;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.exception.AuthenticationException;
import com.bezrukov.microserviceorders.exception.InvalidRefreshTokenException;
import com.bezrukov.microserviceorders.exception.UserAlreadyExistsException;

/**
 * Сервис аутентификации.
 */
public interface AuthService {
    /**
     * Получить текущего аутентифицированного пользователя.
     *
     * @return текущий пользователь
     * @throws AuthenticationException если пользователь не аутентифицирован
     */
    User getCurrentUser();

    /**
     * Зарегистрировать нового пользователя.
     *
     * @param registerRequest данные для регистрации
     * @return созданный пользователь
     * @throws UserAlreadyExistsException если пользователь уже существует
     */
    User register(AuthRequest registerRequest);

    /**
     * Выполнить вход в систему.
     *
     * @param username имя пользователя
     * @param password пароль
     * @return токены доступа
     * @throws AuthenticationException если учетные данные неверны
     */
    AuthResponse login(String username, String password);

    /**
     * Обновить токен доступа.
     *
     * @param refreshToken refresh токен
     * @return новая пара токенов
     * @throws InvalidRefreshTokenException если refresh токен недействителен
     */
    AuthResponse refreshToken(String refreshToken);
}
