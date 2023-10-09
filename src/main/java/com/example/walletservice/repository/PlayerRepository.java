package com.example.walletservice.repository;

import com.example.walletservice.model.User;

/**
 * Интерфейс, представляющий репозиторий для работы с пользователями.
 */
public interface PlayerRepository {
    /**
     * Регистрирует нового игрока с указанным именем пользователя и паролем.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     */
    void registerPlayer(String username, String password);

    /**
     * Проверяет аутентификацию игрока с указанным именем пользователя и паролем.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return true, если аутентификация успешна, иначе false
     */
    boolean authenticatePlayer(String username, String password);

    /**
     * Возвращает игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return объект пользователя, если найден, иначе null
     */
    User getPlayer(String username);
}