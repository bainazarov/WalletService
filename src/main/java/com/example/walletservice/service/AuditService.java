package com.example.walletservice.service;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий сервис аудита.
 */
@Data
public class AuditService {
    private final List<String> auditLogs;
    private final DateTimeFormatter formatter;

    /**
     * Создает новый объект `AuditService`.
     */
    public AuditService() {
        this.auditLogs = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Записывает аудитовую запись для успешной аутентификации пользователя.
     *
     * @param username имя пользователя
     */
    public void logAuthentication(String username) {
        LocalDateTime currentTime = LocalDateTime.now();
        String log = currentTime.format(formatter) + " - Авторизация пользователя " + username;
        auditLogs.add(log);
    }

    /**
     * Записывает аудитовую запись для выхода пользователя из системы.
     *
     * @param username имя пользователя
     */
    public void logLogout(String username) {
        LocalDateTime currentTime = LocalDateTime.now();
        String log = currentTime.format(formatter) + " - Завершение работы пользователя " + username;
        auditLogs.add(log);
    }

    /**
     * Записывает аудитовую запись для транзакции пользователя.
     *
     * @param username        имя пользователя
     * @param transactionType тип транзакции
     * @param amount          сумма транзакции
     */
    public void logTransaction(String username, String transactionType, double amount) {
        LocalDateTime currentTime = LocalDateTime.now();
        String log = currentTime.format(formatter) + " - Пользователь " + username + " выполнил " + transactionType + " в размере " + amount;
        auditLogs.add(log);
    }

    /**
     * Выводит все аудитовые записи.
     */
    public void printAuditLogs() {
        System.out.println("Аудитовые записи:");
        for (String log : auditLogs) {
            System.out.println(log);
        }
    }
}