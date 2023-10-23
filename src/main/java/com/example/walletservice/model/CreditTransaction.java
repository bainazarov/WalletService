package com.example.walletservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс CreditTransaction представляет собой модель кредитной транзакции.
 * Он содержит информацию о сумме транзакции и идентификаторе транзакции.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditTransaction  {
    private double amount;
    private String transactionId;

}