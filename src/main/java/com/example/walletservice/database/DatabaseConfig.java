package com.example.walletservice.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс DbConfig представляет собой конфигурацию базы данных.
 * Он содержит методы для получения URL, имени пользователя и пароля для подключения к базе данных.
 */
public class DatabaseConfig {

    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Возвращает URL базы данных.
     *
     * @return URL базы данных
     */
    public static String getUrl() {
        return properties.getProperty("db.url");
    }


    /**
     * Возвращает имя пользователя для подключения к базе данных.
     *
     * @return имя пользователя
     */
    public static String getUsername() {
        return properties.getProperty("db.username");
    }


    /**
     * Возвращает пароль для подключения к базе данных.
     *
     * @return пароль
     */
    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}
