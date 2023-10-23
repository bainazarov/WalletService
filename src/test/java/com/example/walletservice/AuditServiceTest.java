package com.example.walletservice;


import com.example.walletservice.config.ContainersEnvironment;
import com.example.walletservice.repository.AuditRepository;
import com.example.walletservice.repository.AuditRepositoryImpl;
import com.example.walletservice.repository.PlayerRepository;
import com.example.walletservice.repository.PlayerRepositoryImpl;
import com.example.walletservice.service.AuditService;
import com.example.walletservice.service.AuditServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;


public class AuditServiceTest extends ContainersEnvironment {
    private static PlayerRepository playerRepository;
    private static AuditRepository auditService;

    @BeforeEach
    public void setUp() {
        playerRepository = PlayerRepositoryImpl.getInstance();
        playerRepository.deleteAll();
        auditService = new AuditRepositoryImpl();
    }

    @AfterAll
    static void tearDown() {
        playerRepository = PlayerRepositoryImpl.getInstance();
        playerRepository.deleteAll();
    }

    @Test
    public void logAuthenticationPositive() {
        // TODO
    }

    @Test
    public void logAuthenticationNegative() {
        // TODO
    }

    @Test
    public void logLogoutPositive() {
        // TODO

    }

    @Test
    public void logLogoutNegative() {
        // TODO

    }

    @Test
    public void logTransactionPositive() {
        // TODO

    }

    @Test
    public void logTransactionNegative() {
        // TODO

    }

    @Test
    public void printAuditLogsPositive() {
        // TODO

    }

    @Test
    public void printAuditLogsNegative() {
        // TODO

    }
}
