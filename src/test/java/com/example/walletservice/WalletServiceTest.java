package com.example.walletservice;

import com.example.walletservice.config.ContainersEnvironment;
import com.example.walletservice.model.User;
import com.example.walletservice.repository.AuditRepositoryImpl;
import com.example.walletservice.repository.PlayerRepository;
import com.example.walletservice.repository.PlayerRepositoryImpl;
import com.example.walletservice.service.AuditService;
import com.example.walletservice.service.AuditServiceImpl;
import com.example.walletservice.service.WalletService;
import com.example.walletservice.service.WalletServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WalletServiceTest extends ContainersEnvironment {

    private static WalletService walletService;
    private static PlayerRepository playerRepository;
    private static AuditService auditService;

    @BeforeEach
    public void setUp() {
        playerRepository = PlayerRepositoryImpl.getInstance();
        auditService = new AuditServiceImpl(new AuditRepositoryImpl());
        walletService = new WalletServiceImpl(playerRepository, auditService);
    }

    @AfterAll
    static void tearDown() {
        playerRepository = PlayerRepositoryImpl.getInstance();
        playerRepository.deleteAll();
    }


    @Test
    public void testRegisterPlayerPositive() {
        String username = "testUser";
        String password = "testPassword";

        walletService.registerPlayer(username, password);

        User user = playerRepository.get(username);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(0.0, user.getBalance());
    }

    @Test
    public void testRegisterPlayerNegative() {
        String username = "testUser";
        String password = "testPassword";

        walletService.registerPlayer(username, password);

        assertThrows(NoSuchElementException.class, () -> {
            walletService.registerPlayer(username, "newPassword");
        });
    }

    @Test
    public void testAuthenticatePlayerPositive() {
        String username = "testUser";
        String password = "testPassword";

        walletService.registerPlayer(username, password);

        boolean isAuthenticated = walletService.authenticatePlayer(username, password);
        assertTrue(isAuthenticated);
    }

    @Test
    public void testAuthenticatePlayerNegative() {
        String username = "testUser";
        String password = "testPassword";

        walletService.registerPlayer(username, password);

        boolean isAuthenticated = walletService.authenticatePlayer(username, "wrongPassword");

        assertTrue(isAuthenticated);
    }

    @Test
    public void testGetPlayerBalancePositive() {
        String username = "testUser";
        String password = "testPassword";

        walletService.registerPlayer(username, password);

        double balance = walletService.getPlayerBalance(username);
        assertEquals(0.0, balance);
    }

    @Test
    public void testGetPlayerBalanceNegative() {
        String username = "testUser";
        String password = "testPassword";

        walletService.registerPlayer(username, password);

        double balance = walletService.getPlayerBalance(username);
        assertEquals(10.0, balance);
    }
}