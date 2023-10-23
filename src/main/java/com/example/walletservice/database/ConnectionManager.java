package com.example.walletservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс DbConfig представляет собой конфигурацию базы данных.
 * Он содержит методы для получения URL, имени пользователя и пароля для подключения к базе данных.
 */
public class ConnectionManager {

    private static String URL = DatabaseConfig.getUrl();
    private static String USERNAME = DatabaseConfig.getUsername();
    private static String PASSWORD = DatabaseConfig.getPassword();

    public ConnectionManager(String url, String username, String password) {
        this.URL = url;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
