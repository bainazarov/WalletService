package com.example.walletservice.service;

import com.example.walletservice.model.User;
import com.example.walletservice.repository.PlayerRepository;



public class WalletServiceImpl implements WalletService {
    private PlayerRepository playerRepository;
    private AuditService auditService;


    public WalletServiceImpl(PlayerRepository playerRepository, AuditService auditService) {
        this.playerRepository = playerRepository;
        this.auditService = auditService;
    }

    @Override
    public void registerPlayer(String username, String password) {
        playerRepository.registerPlayer(username, password);
        auditService.logAuthentication(username);
    }

    @Override
    public boolean authenticatePlayer(String username, String password) {
        boolean isAuthenticated = playerRepository.authenticatePlayer(username, password);
        if (isAuthenticated) {
            auditService.logAuthentication(username);
        }
        return isAuthenticated;
    }

    @Override
    public double getPlayerBalance(String username) {
        User user = playerRepository.getPlayer(username);
        if (user != null) {
            return user.getBalance();
        }
        return 0;
    }

}