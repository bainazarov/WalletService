package com.example.walletservice.service;



/**
 * Интерфейс WalletService отвечает за выполнение операций с кошельком пользователя.
 */
public interface WalletService {

    /**
     * Регистрирует нового игрока.
     * @param username Имя пользователя
     * @param password Пароль пользователя
     */
    void registerPlayer(String username, String password);

    /**
     * Проверяет аутентификацию игрока.
     * @param username Имя пользователя
     * @param password Пароль пользователя
     * @return true, если аутентификация успешна, иначе false
     */
    boolean authenticatePlayer(String username, String password);

    /**
     * Возвращает баланс пользователя.
     * @param username Имя пользователя
     * @return Баланс пользователя
     */
    double getPlayerBalance(String username);
}