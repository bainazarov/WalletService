package com.example.walletservice.repository;


import com.example.walletservice.database.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditRepositoryImpl implements AuditRepository {
    private final DateTimeFormatter formatter;

    public AuditRepositoryImpl() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }


    @Override
    public void logAuthentication(String username) {
        LocalDateTime currentTime = LocalDateTime.now();
        String log = " - Авторизация пользователя " + username;

        String sql = "INSERT INTO wallet.audit_logs (date_time, log_text) VALUES (?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setTimestamp(1, Timestamp.valueOf(currentTime));
            statement.setString(2, log);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось зарегистрировать аутентификацию.", e);
        }
    }

    @Override
    public void logLogout(String username) {
        LocalDateTime currentTime = LocalDateTime.now();
        String log =" - Завершение работы пользователя " + username;

        String sql = "INSERT INTO wallet.audit_logs (date_time, log_text) VALUES (?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(currentTime));
            statement.setString(2, log);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось войти в систему.", e);
        }
    }

    @Override
    public void logTransaction(String username, String transactionType, double amount) {
        LocalDateTime currentTime = LocalDateTime.now();
        String log = " - Пользователь " + username + " выполнил " + transactionType + " в размере " + amount;

        String sql = "INSERT INTO wallet.audit_logs (date_time, log_text) VALUES (?, ?)";
        try (Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setTimestamp(1, Timestamp.valueOf(currentTime));
            statement.setString(2, log);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось зарегистрировать транзакцию.", e);
        }
    }

    @Override
    public List<String> getAuditLogs() {
        List<String> auditLogs = new ArrayList<>();

        String sql = "SELECT * FROM wallet.audit_logs";
        try (Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                String logText = resultSet.getString("log_text");
                auditLogs.add(id + " - " + dateTime.format(formatter) + " - " + logText);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить журналы аудита.", e);
        }

        return auditLogs;
    }
}
