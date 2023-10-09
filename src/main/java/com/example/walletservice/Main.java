package com.example.walletservice;

import com.example.walletservice.In.WalletConsole;

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
        WalletConsole walletConsole = new WalletConsole();
        walletConsole.start();
    }
}