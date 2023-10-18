package com.example.walletservice;

import com.example.walletservice.In.WalletConsole;
import com.example.walletservice.liquibase.MyLiquibase;

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
        MyLiquibase liquibase = new MyLiquibase();
        liquibase.startLiquibase();
        WalletConsole walletConsole = new WalletConsole();
        walletConsole.start();
    }
}