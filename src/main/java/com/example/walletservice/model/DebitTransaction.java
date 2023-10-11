package com.example.walletservice.model;

import lombok.Data;

/**
 * Класс, представляющий дебетовую транзакцию.
 */
@Data
public class DebitTransaction implements Transaction {
    private double amount;
    private String transactionId;

    /**
     * Создает новый объект `DebitTransaction` с указанной суммой и идентификатором транзакции.
     *
     * @param amount        сумма транзакции
     * @param transactionId идентификатор транзакции
     */
    public DebitTransaction(double amount, String transactionId) {
        this.amount = amount;
        this.transactionId = transactionId;
    }

    /**
     * Выполняет дебетовую транзакцию для указанного пользователя.
     *
     * @param user пользователь, для которого выполняется транзакция
     */
    @Override
    public void execute(User user) {
        debit(user);
    }

    /**
     * Выполняет дебетовую операцию для указанного пользователя.
     * Если транзакция с таким идентификатором уже существует, выводит сообщение об ошибке.
     * Если на счету пользователя недостаточно средств, выводит сообщение о недостатке средств.
     *
     * @param user пользователь, для которого выполняется операция
     */
    public void debit(User user) {
        if (user.getTransactionIds().contains(transactionId)) {
            System.out.println("Ошибка: Транзакция с таким идентификатором уже существует");
            return;
        }
        if (user.getBalance() - amount >= 0) {
            user.setBalance(user.getBalance() - amount);
            user.getTransactions().put(transactionId, this);
            user.getTransactionIds().add(transactionId);
            System.out.println("Дебетовая транзакция успешна");
        } else {
            System.out.println("Недостаточно средств");
        }
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