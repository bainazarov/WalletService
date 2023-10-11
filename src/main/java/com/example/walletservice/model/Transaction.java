package com.example.walletservice.model;

/**
 * Интерфейс, представляющий транзакцию.
 */
public interface Transaction {
    /**
     * Выполняет транзакцию для указанного пользователя.
     *
     * @param user пользователь, для которого выполняется транзакция
     */
    void execute(User user);

    /**
     * Возвращает сумму транзакции.
     *
     * @return сумма транзакции
     */
    double getAmount();
}