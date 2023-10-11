package com.example.walletservice.service;

import com.example.walletservice.model.CreditTransaction;
import com.example.walletservice.model.DebitTransaction;
import com.example.walletservice.model.User;
import com.example.walletservice.model.Transaction;
import com.example.walletservice.repository.PlayerRepository;

/**
 * Реализация интерфейса `WalletService` для работы с кошельком пользователя.
 */
public class WalletServiceImpl implements WalletService {
    private PlayerRepository playerRepository;
    private AuditService auditService;

    /**
     * Создает новый объект `WalletServiceImpl`.
     */
    public WalletServiceImpl() {
    }

    /**
     * Создает новый объект `WalletServiceImpl` с указанным репозиторием игроков и сервисом аудита.
     *
     * @param playerRepository репозиторий игроков
     * @param auditService     сервис аудита
     */
    public WalletServiceImpl(PlayerRepository playerRepository, AuditService auditService) {
        this.playerRepository = playerRepository;
        this.auditService = auditService;
    }

    /**
     * Регистрирует нового игрока с указанным именем пользователя и паролем.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     */
    @Override
    public void registerPlayer(String username, String password) {
        playerRepository.registerPlayer(username, password);
        auditService.logAuthentication(username);
    }

    /**
     * Проверяет аутентификацию игрока с указанным именем пользователя и паролем.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return true, если аутентификация успешна, иначе false
     */
    @Override
    public boolean authenticatePlayer(String username, String password) {
        boolean isAuthenticated = playerRepository.authenticatePlayer(username, password);
        if (isAuthenticated) {
            auditService.logAuthentication(username);
        }
        return isAuthenticated;
    }

    /**
     * Возвращает баланс игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return баланс игрока
     */
    @Override
    public double getPlayerBalance(String username) {
        User user = playerRepository.getPlayer(username);
        if (user != null) {
            return user.getBalance();
        }
        return 0;
    }

    /**
     * Выполняет транзакцию для игрока с указанным именем пользователя.
     *
     * @param username    имя пользователя
     * @param transaction транзакция для выполнения
     */
    @Override
    public void executeTransaction(String username, Transaction transaction) {
        User user = playerRepository.getPlayer(username);
        if (user != null) {
            transaction.execute(user);
            auditService.logTransaction(username, transaction.getClass().getSimpleName(), transaction.getAmount());
        }
    }

    /**
     * Выводит историю транзакций для игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     */
    @Override
    public void viewPlayerHistory(String username) {
        User user = playerRepository.getPlayer(username);
        if (user != null) {
            user.viewTransactionHistory();
        }
    }

    /**
     * Создает объект дебетовой транзакции с указанной суммой и идентификатором транзакции.
     *
     * @param amount        сумма транзакции
     * @param transactionId идентификатор транзакции
     * @return объект дебетовой транзакции
     */
    @Override
    public Transaction debitTransaction(double amount, String transactionId) {
        return new DebitTransaction(amount, transactionId);
    }

    /**
     * Создает объект кредитной транзакции с указанной суммой и идентификатором транзакции.
     *
     * @param amount        сумма транзакции
     * @param transactionId идентификатор транзакции
     * @return объект кредитной транзакции
     */
    @Override
    public Transaction creditTransaction(double amount, String transactionId) {
        return new CreditTransaction(amount, transactionId);
    }
}