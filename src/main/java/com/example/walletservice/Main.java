package com.example.walletservice;

import com.example.walletservice.In.WalletConsole;
import com.example.walletservice.liquibase.Liquibase;

/**
 * Главный класс приложения.
 */
public class Main {
    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Liquibase liquibase = new Liquibase();
        liquibase.start();
        WalletConsole walletConsole = new WalletConsole();
        walletConsole.start();
    }
}