package com.example.walletservice.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс DbConfig представляет собой конфигурацию базы данных.
 * Он содержит методы для получения URL, имени пользователя и пароля для подключения к базе данных.
 */
public class DbConfig {

    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream inputStream = DbConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDbPassword() {
        return properties.getProperty("db.password");
    }
}
