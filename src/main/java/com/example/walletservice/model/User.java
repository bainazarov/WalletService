package com.example.walletservice.model;

import lombok.Data;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс, представляющий пользователя.
 */
@Data
public class User {
    private String username;
    private String password;
    private double balance;
    private Map<String, Transaction> transactions;
    private Set<String> transactionIds;

    /**
     * Создает новый объект `User` с указанным именем пользователя и паролем.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.transactions = new HashMap<>();
        this.transactionIds = new HashSet<>();
    }

    /**
     * Выводит историю транзакций для данного пользователя.
     */
    public void viewTransactionHistory() {
        System.out.println("История транзакций для " + username + ":");
        transactions.values().forEach(transaction -> {
            if (transaction instanceof CreditTransaction) {
                System.out.println("Пополнение на сумму " + ((CreditTransaction) transaction).getAmount());
            } else if (transaction instanceof DebitTransaction) {
                System.out.println("Списание на сумму " + ((DebitTransaction) transaction).getAmount());
            }
        });
    }
}