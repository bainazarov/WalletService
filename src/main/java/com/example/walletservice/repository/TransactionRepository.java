package com.example.walletservice.repository;

public interface TransactionRepository {

    /**
     * Сохраняет идентификатор транзакции игрока.
     * @param playerId Идентификатор игрока
     * @param transactionId Идентификатор транзакции
     * @param transactionType Тип транзакции
     * @param transactionAmount Сумма транзакции
     */
    void saveTransactionId(int playerId, String transactionId, String transactionType, double transactionAmount);
}
