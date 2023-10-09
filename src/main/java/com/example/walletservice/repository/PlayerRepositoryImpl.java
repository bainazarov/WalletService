package com.example.walletservice.repository;

import com.example.walletservice.model.User;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация интерфейса `PlayerRepository` для работы с пользователями.
 */
public class PlayerRepositoryImpl implements PlayerRepository {
    private final Map<String, User> players;

    /**
     * Создает новый объект `PlayerRepositoryImpl`.
     */
    public PlayerRepositoryImpl() {
        players = new HashMap<>();
    }

    /**
     * Регистрирует нового игрока с указанным именем пользователя и паролем.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     */
    @Override
    public void registerPlayer(String username, String password) {
        User user = new User(username, password);
        players.put(username, user);
        System.out.println("Вы успешно зарегистрировались, " + username + "!");
    }

    /**
     * Проверяет аутентификацию игрока с указанным именем пользователя и паролем.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return true, если аутентификация успешна, иначе false
     */
    @Override
    public boolean authenticatePlayer(String username, String password) {
        User user = players.get(username);
        boolean isAuthenticated = user != null && user.getPassword().equals(password);
        if (isAuthenticated) {
            System.out.println("Авторизация успешна!");
        } else {
            System.out.println("Неверный логин или пароль.");
        }
        return isAuthenticated;
    }

    /**
     * Возвращает игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return объект пользователя, если найден, иначе null
     */
    @Override
    public User getPlayer(String username) {
        return players.get(username);
    }
}