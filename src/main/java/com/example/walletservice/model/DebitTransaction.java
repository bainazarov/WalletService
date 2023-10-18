package com.example.walletservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DebitTransaction представляет собой модель дебетовой транзакции.
 * Он содержит информацию о сумме транзакции и идентификаторе транзакции.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitTransaction {
    private double amount;
    private String transactionId;

}