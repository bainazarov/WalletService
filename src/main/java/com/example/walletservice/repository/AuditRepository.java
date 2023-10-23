package com.example.walletservice.repository;

import java.util.List;

/**
 * Интерфейс AuditRepository отвечает за журналирование аудита системы кошелька.
 */
public interface AuditRepository {

    /**
     * Записывает информацию об аутентификации пользователя в журнал аудита.
     * @param username Имя пользователя
     */
    void logAuthentication(String username);

    /**
     * Записывает информацию о выходе пользователя из системы в журнал аудита.
     * @param username Имя пользователя
     */
    void logLogout(String username);
    
    /**
     * Записывает информацию о транзакции пользователя в журнал аудита.
     * @param username Имя пользователя
     * @param transactionType Тип транзакции
     * @param amount Сумма транзакции
     */
    void logTransaction(String username, String transactionType, double amount);

    /**
     * Возвращает список журналов аудита.
     * @return Список журналов аудита
     */
    List<String> getAuditLogs();
}
