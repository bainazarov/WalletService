package com.example.walletservice;

import com.example.walletservice.model.CreditTransaction;
import com.example.walletservice.model.DebitTransaction;
import com.example.walletservice.model.User;
import com.example.walletservice.repository.PlayerRepository;
import com.example.walletservice.repository.PlayerRepositoryImpl;
import com.example.walletservice.service.AuditService;
import com.example.walletservice.service.WalletService;
import com.example.walletservice.service.WalletServiceImpl;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WalletServiceTest {
    private PlayerRepository playerRepository;
    private AuditService auditService;
    private WalletService walletService;


    @Before
    public void setUp() {
        playerRepository = new PlayerRepositoryImpl();
        auditService = new AuditService();
        walletService = new WalletServiceImpl(playerRepository, auditService);
    }

    @Test
    public void registerPlayerTest() {
        walletService.registerPlayer("Abdurakhmon", "qwerty123");
        User user = playerRepository.getPlayer("Abdurakhmon");

        assertEquals("Abdurakhmon", user.getUsername());
    }

    @Test
    public void authenticatePlayerTest() {
        walletService.registerPlayer("Abdurakhmon", "qwerty123");
        boolean isAuthenticate = walletService.authenticatePlayer("Abdurakhmon", "qwerty123");

        assertTrue(isAuthenticate);
    }

    @Test
    public void creditTransactionTest() {
        User user = new User("Abdurakhmon", "qwerty123");
        double initialBalance = 100;
        user.setBalance(initialBalance);
        double creditAmount = 50.0;
        String transactionId = "1";

        CreditTransaction debitTransaction = new CreditTransaction(creditAmount, transactionId);
        debitTransaction.execute(user);

        assertEquals(150.0, user.getBalance(), 0);
        assertTrue(user.getTransactions().containsKey(transactionId));
    }

    @Test
    public void debitTransactionTest() {
        User user = new User("Abdurakhmon", "qwerty123");
        double initialBalance = 100;
        user.setBalance(initialBalance);
        double debitAmount = 50.0;
        String transactionId = "1";

        DebitTransaction debitTransaction = new DebitTransaction(debitAmount, transactionId);
        debitTransaction.execute(user);

        assertEquals(50.0, user.getBalance(), 0);
        assertTrue(user.getTransactions().containsKey(transactionId));
    }

    @Test
    public void auditCountTest() {
        auditService.logAuthentication("Abdurakhmon");
        auditService.logLogout("Abdurakhmon");
        auditService.logTransaction("Abdurakhmon", walletService.creditTransaction(100,"1").toString(), 100);

        assertEquals(3, auditService.getAuditLogs().size());
    }

    @Test
    public void viewTransactionHistoryTest() {
        User user = new User("Abdurakhmon", "qwerty123");
        walletService.registerPlayer(user.getUsername(), user.getPassword());

        CreditTransaction transaction = new CreditTransaction(100.0, "1");
        walletService.executeTransaction(user.getUsername(), transaction);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expectedHistory = "История транзакций для Abdurakhmon:" + System.lineSeparator() +
                "Пополнение на сумму 100.0" + System.lineSeparator();

        walletService.viewPlayerHistory(user.getUsername());

        assertEquals(expectedHistory, outContent.toString());
    }
}
