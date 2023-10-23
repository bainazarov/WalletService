package com.example.walletservice.In;

import com.example.walletservice.repository.AuditRepository;
import com.example.walletservice.repository.AuditRepositoryImpl;
import com.example.walletservice.repository.PlayerRepository;
import com.example.walletservice.repository.PlayerRepositoryImpl;
import com.example.walletservice.service.*;

import java.util.Scanner;


/**
 * Класс WalletConsole представляет собой консольное приложение для работы с кошельком пользователей.
 * Позволяет регистрировать и аутентифицировать пользователей, выполнять транзакции и просматривать аудитовые записи.
 */
public class WalletConsole {
    PlayerRepository playerRepository = new PlayerRepositoryImpl();
    AuditRepository auditRepository = new AuditRepositoryImpl();
    AuditService auditService = new AuditServiceImpl(auditRepository);
    WalletService walletService = new WalletServiceImpl(playerRepository, auditService);
    TransactionService transactionService = new TransactionServiceImpl(playerRepository, auditService);


    /**
     * Запускает консольное приложение для работы с кошельком.
     */
    public void start() {
        Scanner scanner =  new Scanner(System.in);
        while (true) {
            System.out.println("1. Регистрация");
            System.out.println("2. Войти");
            System.out.println("Q. Выйти");
            System.out.print("Введите значение: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Введите логин: ");
                String username = scanner.nextLine();
                System.out.print("Введите пороль: ");
                String password = scanner.nextLine();
                walletService.registerPlayer(username, password);
            } else if (choice.equals("2")) {
                System.out.print("Введите логин: ");
                String username = scanner.nextLine();
                System.out.print("Введите пороль: ");
                String password = scanner.nextLine();

                if (username.equals("admin") && password.equals("admin")) {
                    while (true) {
                        System.out.println("1. Вывести аудит");
                        System.out.println("2. Выйти");
                        System.out.print("Введите значение: ");
                        String adminChoice = scanner.nextLine();

                        if (adminChoice.equals("1")) {
                            auditService.printAuditLogs();
                        } else if (adminChoice.equals("2")) {
                            break;
                        } else {
                            System.out.println("Неверная команда");
                        }
                    }
                } else {
                    boolean isAuthenticated = walletService.authenticatePlayer(username, password);

                    if (isAuthenticated) {
                        label:
                        while (true) {
                            System.out.println("1. Баланс");
                            System.out.println("2. Списать");
                            System.out.println("3. Пополнить");
                            System.out.println("4. История пополнения/снятия средств");
                            System.out.println("5. Выйти");
                            System.out.print("Введите значение ");
                            String command = scanner.nextLine();

                            switch (command) {
                                case "1":
                                    double balance = walletService.getPlayerBalance(username);
                                    System.out.println("Ваш баланс " + balance);
                                    break;
                                case "2": {
                                    System.out.print("Введите сумму для списания: ");
                                    double amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    System.out.print("Введите идентификатор транзакции: ");
                                    String transactionId = scanner.nextLine();

                                    transactionService.executeTransaction(username, amount, transactionId, "debit");
                                    break;
                                }
                                case "3": {
                                    System.out.print("Введите сумму для пополнения: ");
                                    double amount = scanner.nextDouble();
                                    scanner.nextLine();
                                    System.out.print("Введите идентификатор транзакции: ");
                                    String transactionId = scanner.nextLine();

                                    transactionService.executeTransaction(username, amount, transactionId, "credit");
                                    break;
                                }
                                case "4":
                                    transactionService.viewPlayerHistory(username);
                                    break;
                                case "5":
                                    auditService.logLogout(username);
                                    break label;
                                default:
                                    System.out.println("Неверная команда");
                                    break;
                            }
                        }
                    }
                }
            } else if (choice.equalsIgnoreCase("Q")) {
                break;
            } else {
                System.out.println("Неверная команда");
            }
        }
        scanner.close();
    }
}
