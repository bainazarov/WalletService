package com.example.walletservice;

import com.example.walletservice.database.ConnectionManager;
import com.example.walletservice.model.User;
import com.example.walletservice.repository.AuditRepositoryImpl;
import com.example.walletservice.repository.PlayerRepository;
import com.example.walletservice.repository.PlayerRepositoryImpl;
import com.example.walletservice.service.AuditService;
import com.example.walletservice.service.AuditServiceImpl;
import com.example.walletservice.service.WalletService;
import com.example.walletservice.service.WalletServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class WalletServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13.3");
    private static WalletService walletService;
    private static PlayerRepository playerRepository;
    private static AuditService auditService;

    @BeforeAll
    static void setUp() {
        postgresContainer.start();

        ConnectionManager connection = new ConnectionManager(postgresContainer.getJdbcUrl(),
        postgresContainer.getPassword(), postgresContainer.getPassword());

        playerRepository = new PlayerRepositoryImpl(connection);
        auditService = new AuditServiceImpl(new AuditRepositoryImpl());
        walletService = new WalletServiceImpl(playerRepository, auditService);
    }

    @AfterAll
    static void tearDown() {
        postgresContainer.stop();
    }

    @Test
    public void testRegisterPlayer() {
        String username = "testUser";
        String password = "testPassword";

        walletService.registerPlayer(username, password);

        User user = playerRepository.getPlayer(username);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(0.0, user.getBalance());
    }

    @Test
    public void testAuthenticatePlayer() {
        String username = "testUser";
        String password = "testPassword";

        walletService.registerPlayer(username, password);

        boolean isAuthenticated = walletService.authenticatePlayer(username, password);
        assertTrue(isAuthenticated);
    }

    @Test
    public void testGetPlayerBalance() {
        String username = "testUser";
        String password = "testPassword";

        walletService.registerPlayer(username, password);

        double balance = walletService.getPlayerBalance(username);
        assertEquals(0.0, balance);
    }
}