package com.example.walletservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * Класс User представляет собой модель пользователя.
 * Он содержит информацию о идентификаторе игрока, имени пользователя, пароле, балансе,
 * идентификаторах транзакций и типах транзакций.
 */
@Data
@AllArgsConstructor
public class User {
    private int playerId;
    private String username;
    private String password;
    private double balance;
    private Set<String> transactionIds;
    private Map<String, String> transactionTypes;
}