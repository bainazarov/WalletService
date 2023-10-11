package com.example.walletservice.service;

import com.example.walletservice.model.Transaction;

/**
 * Интерфейс, представляющий сервис кошелька.
 */
public interface WalletService {
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
     * Возвращает баланс игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return баланс игрока
     */
    double getPlayerBalance(String username);

    /**
     * Выполняет транзакцию для игрока с указанным именем пользователя.
     *
     * @param username    имя пользователя
     * @param transaction транзакция для выполнения
     */
    void executeTransaction(String username, Transaction transaction);

    /**
     * Выводит историю транзакций для игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     */
    void viewPlayerHistory(String username);

    /**
     * Создает объект дебетовой транзакции с указанной суммой и идентификатором транзакции.
     *
     * @param amount        сумма транзакции
     * @param transactionId идентификатор транзакции
     * @return объект дебетовой транзакции
     */
    Transaction debitTransaction(double amount, String transactionId);

    /**
     * Создает объект кредитной транзакции с указанной суммой и идентификатором транзакции.
     *
     * @param amount        сумма транзакции
     * @param transactionId идентификатор транзакции
     * @return объект кредитной транзакции
     */
    Transaction creditTransaction(double amount, String transactionId);
}