package com.example.walletservice;

import com.example.walletservice.config.ContainersEnvironment;
import com.example.walletservice.model.User;
import com.example.walletservice.repository.AuditRepositoryImpl;
import com.example.walletservice.repository.PlayerRepository;
import com.example.walletservice.repository.PlayerRepositoryImpl;
import com.example.walletservice.service.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;


public class TransactionTest extends ContainersEnvironment {
    private static TransactionService transactionService;
    private static PlayerRepository playerRepository;
    private static AuditService auditService;
    private static WalletService walletService;



    @BeforeEach
    public void setUp() {
        playerRepository = PlayerRepositoryImpl.getInstance();
        auditService = new AuditServiceImpl(new AuditRepositoryImpl());
        transactionService = new TransactionServiceImpl(playerRepository, auditService);
        walletService = new WalletServiceImpl(playerRepository, auditService);
    }

    @AfterAll
    static void tearDown() {
        playerRepository = PlayerRepositoryImpl.getInstance();
        playerRepository.deleteAll();
    }

    @Test
    public void executionTransactionTestPositive() {
        String username = "testUser";
        String password = "testPassword";
        double initialBalance = 100.0;
        double amount = 50.0;
        String transactionId = "12345";
        String transactionType = "debit";

        playerRepository.registerPlayer(username, password);
        User user = playerRepository.get(username);
        user.setBalance(initialBalance);
        playerRepository.updatePlayer(user);

        transactionService.executeTransaction(username, amount, transactionId, transactionType);

        double expectedBalance = initialBalance - amount;
        double actualBalance = walletService.getPlayerBalance(username);
        assertEquals(expectedBalance, actualBalance, 0.001);

    }

    @Test
    public void executionTransactionTestNegative() {
        String username = "testUser";
        String password = "testPassword";
        double initialBalance = 100.0;
        double amount = 150.0;
        String transactionId = "123";
        String transactionType = "debit";

        playerRepository.registerPlayer(username, password);
        User user = playerRepository.get(username);
        user.setBalance(initialBalance);
        playerRepository.updatePlayer(user);

        transactionService.executeTransaction(username, amount, transactionId, transactionType);

        double expectedBalance = initialBalance;
        double actualBalance = walletService.getPlayerBalance(username);
        assertEquals(expectedBalance, actualBalance, 0.001);

    }
}
