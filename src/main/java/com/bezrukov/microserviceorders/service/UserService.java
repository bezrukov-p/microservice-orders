package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.entity.Role;
import com.bezrukov.microserviceorders.entity.User;
import com.bezrukov.microserviceorders.exception.UserAlreadyExistsException;
import com.bezrukov.microserviceorders.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления пользователями.
 */
public interface UserService {
    /**
     * Получить пользователя по имени.
     *
     * @param username имя пользователя
     * @return пользователь
     * @throws UserNotFoundException если пользователь не найден
     */
    User getUser(String username);

    /**
     * Получить пользователя по имени.
     *
     * @param id id пользователя
     * @return пользователь
     * @throws UserNotFoundException если пользователь не найден
     */
    User getUser(UUID id);

    /**
     * Получить всех пользователей.
     *
     * @return список всех пользователей
     */
    List<User> getAllUsers();

    /**
     * Создать нового пользователя.
     *
     * @param username имя пользователя
     * @param password пароль
     * @param role     роль пользователя
     * @return созданный пользователь
     * @throws UserAlreadyExistsException если пользователь с таким именем уже существует
     */
    User create(String username, String password, Role role);

    /**
     * Удалить пользователя.
     *
     * @param id идентификатор пользователя
     * @return удаленный пользователь
     * @throws UserNotFoundException если пользователь не найден
     */
    User delete(UUID id);
}
