package com.example.walletservice.model;

import lombok.Data;

/**
 * Класс, представляющий кредитную транзакцию.
 */
@Data
public class CreditTransaction implements Transaction {
    private double amount;
    private String transactionId;

    /**
     * Создает новый объект `CreditTransaction` с указанной суммой и идентификатором транзакции.
     *
     * @param amount        сумма транзакции
     * @param transactionId идентификатор транзакции
     */
    public CreditTransaction(double amount, String transactionId) {
        this.amount = amount;
        this.transactionId = transactionId;
    }

    /**
     * Выполняет кредитную транзакцию для указанного пользователя.
     *
     * @param user пользователь, для которого выполняется транзакция
     */
    @Override
    public void execute(User user) {
        credit(user);
    }

    /**
     * Выполняет кредитную операцию для указанного пользователя.
     * Если транзакция с таким идентификатором уже существует, выводит сообщение об ошибке.
     *
     * @param user пользователь, для которого выполняется операция
     */
    public void credit(User user) {
        if (user.getTransactionIds().contains(transactionId)) {
            System.out.println("Ошибка: Транзакция с таким идентификатором уже существует");
            return;
        }
        user.setBalance(user.getBalance() + amount);
        user.getTransactions().put(transactionId, this);
        user.getTransactionIds().add(transactionId);
        System.out.println("Кредитная транзакция успешна");
    }

    /**
     * Возвращает сумму транзакции.
     *
     * @return сумма транзакции
     */
    @Override
    public double getAmount() {
        return amount;
    }
}