package com.example.walletservice.service;

import com.example.walletservice.repository.AuditRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class AuditServiceImpl implements AuditService {

    private AuditRepository auditRepository;

    @Override
    public void logAuthentication(String username) {
        auditRepository.logAuthentication(username);
    }

    @Override
    public void logLogout(String username) {
        auditRepository.logLogout(username);
    }

    @Override
    public void logTransaction(String username, String transactionType, double amount) {
        auditRepository.logTransaction(username, transactionType, amount);
    }


    @Override
    public void printAuditLogs() {
        System.out.println("Аудитовые записи:");
        List<String> auditLogs = auditRepository.getAuditLogs();

        for (String log : auditLogs) {
            System.out.println(log);
        }
    }
}