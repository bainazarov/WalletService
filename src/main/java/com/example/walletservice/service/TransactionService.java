package com.example.walletservice.service;


/**
 * Интерфейс TransactionService отвечает за выполнение операций с транзакциями пользователей.
 */
public interface TransactionService {

    /**
     * Выполняет транзакцию для указанного пользователя.
     * @param username Имя пользователя
     * @param amount Сумма транзакции
     * @param transactionId Идентификатор транзакции
     * @param transactionType Тип транзакции
     */
    void executeTransaction(String username, double amount, String transactionId, String transactionType);


    /**
     * Выводит на консоль историю транзакций указанного пользователя.
     * @param username Имя пользователя
     */
    void viewPlayerHistory(String username);
}
