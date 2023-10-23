package com.example.walletservice;

import com.example.walletservice.config.ContainersEnvironment;
import com.example.walletservice.model.User;
import com.example.walletservice.repository.PlayerRepository;
import com.example.walletservice.repository.PlayerRepositoryImpl;
import com.example.walletservice.repository.TransactionRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;


public class PlayerRepositoryTest extends ContainersEnvironment {
    private static PlayerRepository playerRepository;
    private static TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        playerRepository = PlayerRepositoryImpl.getInstance();
    }

    @AfterAll
    static void tearDown() {
        playerRepository = PlayerRepositoryImpl.getInstance();
        playerRepository.deleteAll();
    }

    @Test
    public void registerPlayerPositive() {
        String username = "testUser";
        String password = "testPassword";

        playerRepository.registerPlayer(username, password);

        User user = playerRepository.get(username);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertEquals(password, user.getPassword());
    }

    @Test
    public void registerPlayerNegative() {
        String username = "testUser";
        String password = "testPassword";

        playerRepository.registerPlayer(username, password);

        Assertions.assertThrows(RuntimeException.class, () -> {
            playerRepository.registerPlayer(username, "newPassword");
        });
    }

    @Test
    public void authenticatePlayerPositive() {
        String username = "testUser";
        String password = "testPassword";

        playerRepository.registerPlayer(username, password);

        boolean isAuthenticated = playerRepository.authenticatePlayer(username, password);
        Assertions.assertTrue(isAuthenticated);
    }

    @Test
    public void authenticatePlayerNegative() {
        String username = "testUser";
        String password = "testPassword";

        playerRepository.registerPlayer(username, password);

        boolean isAuthenticated = playerRepository.authenticatePlayer(username, "wrongPassword");
        Assertions.assertTrue(isAuthenticated);
    }

    @Test
    public void getPlayerPositive() {
        String username = "testUser";
        String password = "testPassword";

        playerRepository.registerPlayer(username, password);

        User user = playerRepository.get(username);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(username, user.getUsername());
    }

    @Test
    public void getPlayerNegative() {
        String username = "nonExistingUser";

        User user = playerRepository.get(username);
        Assertions.assertNotNull(user);
    }

    @Test
    public void saveTransactionIdPositive() {
        String username = "testUser";
        String transactionId = "123456";
        String transactionType = "debit";
        double transactionAmount = 50.0;

        playerRepository.registerPlayer(username, "testPassword");
        int playerId = playerRepository.get(username).getPlayerId();
        transactionRepository.saveTransactionId(playerId, transactionId, transactionType, transactionAmount);

        User user = playerRepository.get(username);
        Assertions.assertNotNull(user);
        Assertions.assertTrue(user.getTransactionIds().contains(transactionId));
        Assertions.assertEquals(transactionType, user.getTransactionTypes().get(transactionId));
    }

    @Test
    public void saveTransactionIdNegative() {
        int playerId = 1;
        String transactionId = "123456";
        String transactionType = "debit";
        double transactionAmount = 50.0;

        transactionRepository.saveTransactionId(playerId, transactionId, transactionType, transactionAmount);

        Assertions.assertThrows(RuntimeException.class, () -> {
            transactionRepository.saveTransactionId(playerId, transactionId, transactionType, transactionAmount);
        });
    }


    @Test
    public void updatePlayerPositive() {
        String username = "testUser";
        String password = "testPassword";
        double initialBalance = 100.0;
        double newBalance = 150.0;

        playerRepository.registerPlayer(username, password);
        User user = playerRepository.get(username);
        user.setBalance(initialBalance);
        playerRepository.updatePlayer(user);

        user.setBalance(newBalance);
        playerRepository.updatePlayer(user);

        User updatedUser = playerRepository.get(username);
        Assertions.assertEquals(newBalance, updatedUser.getBalance());
    }
    @Test
    public void updatePlayerNegative() {
        String username = "nonExistingUser";
        double newBalance = 150.0;

        User user = new User(1, username, "testPassword", newBalance, new HashSet<>(), new HashMap<>());
        Assertions.assertThrows(RuntimeException.class, () -> {
            playerRepository.updatePlayer(user);
        });
    }

    @Test
    public void isPlayerExistsPositive() {
        String username = "testUser";
        String password = "testPassword";

        playerRepository.registerPlayer(username, password);

        boolean exists = playerRepository.isPlayerExists(username, password);
        Assertions.assertFalse(exists);
    }

    @Test
    public void isPlayerExistsNegative() {
        String username = "testUser";
        String password = "testPassword";

        boolean exists = playerRepository.isPlayerExists(username, password);
        Assertions.assertTrue(exists);
    }

    @Test
    public void deleteAllPositive() {
        String username = "testUser";
        String password = "testPassword";

        playerRepository.registerPlayer(username, password);

        playerRepository.deleteAll();

        User deletedUser = playerRepository.get(username);
        Assertions.assertNull(deletedUser);
    }

    @Test
    public void deleteAllNegative() {
        String username = "testUser";
        String password = "testPassword";

        playerRepository.registerPlayer(username, password);
        User user = playerRepository.get(username);
        Assertions.assertNotNull(user);

        playerRepository.deleteAll();

        user = playerRepository.get(username);
        Assertions.assertNotNull(user);
    }

}