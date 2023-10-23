package com.example.walletservice.service;

import com.example.walletservice.model.CreditTransaction;
import com.example.walletservice.model.DebitTransaction;
import com.example.walletservice.model.User;
import com.example.walletservice.repository.PlayerRepository;
import com.example.walletservice.repository.TransactionRepository;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

public class TransactionServiceImpl implements TransactionService{

    private PlayerRepository playerRepository;
    private AuditService auditService;
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(PlayerRepository playerRepository, AuditService auditService) {
        this.playerRepository = playerRepository;
        this.auditService = auditService;
    }

    @Override
    public void executeTransaction(String username, double amount, String transactionId, String transactionType) {
        User user = playerRepository.get(username);
        if (user != null) {
            if (user.getTransactionIds().contains(transactionId)) {
                System.out.println("Ошибка: Транзакция с таким идентификатором уже существует");
                return;
            }

            if (transactionType.equals("debit")) {
                DebitTransaction transaction = new DebitTransaction(amount, transactionId);
                debit(user, transaction);
            } else if (transactionType.equals("credit")) {
                CreditTransaction transaction = new CreditTransaction(amount, transactionId);
                credit(user, transaction);
            }

            playerRepository.updatePlayer(user);
            transactionRepository.saveTransactionId(user.getPlayerId(), transactionId, transactionType, amount);
            auditService.logTransaction(username, transactionType, amount);
        }
    }



    /**
     * Выполняет кредитную транзакцию для указанного пользователя.
     * @param user Объект User, представляющий пользователя
     * @param transaction Объект CreditTransaction, представляющий транзакцию
     */
    private void credit(User user, CreditTransaction transaction) {
        if (user == null) {
            System.out.println("Пользователь не найден");
            return;
        }

        String transactionId = transaction.getTransactionId();

        if (user.getTransactionIds().contains(transactionId)) {
            System.out.println("Ошибка: Транзакция с таким идентификатором уже существует");
            return;
        }

        double balance = user.getBalance();
        double amount = transaction.getAmount();
        user.setBalance(balance + amount);

        user.getTransactionIds().add(transactionId);
        user.getTransactionTypes().put(transactionId, "credit");

        System.out.println("Кредитная транзакция успешна");
    }


    /**
     * Выполняет дебетовую транзакцию для указанного пользователя.
     * @param user Объект User, представляющий пользователя
     * @param transaction Объект DebitTransaction, представляющий транзакцию
     */
    private void debit(User user, DebitTransaction transaction) {
        if (user == null) {
            System.out.println("Пользователь не найден");
            return;
        }

        String transactionId = transaction.getTransactionId();

        if (user.getTransactionIds().contains(transactionId)) {
            System.out.println("Ошибка: Транзакция с таким идентификатором уже существует");
            return;
        }

        double balance = user.getBalance();
        double amount = transaction.getAmount();

        if (balance - amount >= 0) {
            user.setBalance(balance - amount);

            user.getTransactionIds().add(transactionId);
            user.getTransactionTypes().put(transactionId, "debit");

            System.out.println("Дебетовая транзакция успешна");
        } else {
            System.out.println("Недостаточно средств");
        }
    }

    @Override
    public void viewPlayerHistory(String username) {
        User user = playerRepository.get(username);
        if (user != null) {
            System.out.println("История транзакций для " + username + ":");

            Set<String> transactionIds = user.getTransactionIds();
            Map<String, String> transactionTypes = user.getTransactionTypes();

            for (String transactionId : transactionIds) {
                String transactionType = transactionTypes.get(transactionId);
                double transactionAmount = 0;

                if (transactionType.equals("credit")) {
                    CreditTransaction creditTransaction = new CreditTransaction(user.getBalance(), transactionId);
                    transactionAmount = creditTransaction.getAmount();
                } else if (transactionType.equals("debit")) {
                    DebitTransaction debitTransaction = new DebitTransaction(user.getBalance(), transactionId);
                    transactionAmount = debitTransaction.getAmount();
                }

                System.out.println("Идентификатор: " + transactionId + ", тип: " + transactionType + ", сумма: " + transactionAmount);
            }
        }
    }
}
