package com.example.walletservice.repository;

import com.example.walletservice.model.User;


/**
 * Интерфейс PlayerRepository отвечает за взаимодействие с базой данных для операций с игроками.
 */
public interface PlayerRepository {

    /**
     * Регистрирует нового игрока.
     * @param username Имя пользователя
     * @param password Пароль пользователя
     */
    void registerPlayer(String username, String password);

    /**
     * Проверяет аутентификацию игрока.
     * @param username Имя пользователя
     * @param password Пароль пользователя
     * @return true, если аутентификация успешна, иначе false
     */
    boolean authenticatePlayer(String username, String password);

    /**
     * Возвращает информацию об игроке по его имени пользователя.
     * @param username Имя пользователя
     * @return Объект User, представляющий информацию об игроке
     */
    User getPlayer(String username);

    /**
     * Сохраняет идентификатор транзакции игрока.
     * @param playerId Идентификатор игрока
     * @param transactionId Идентификатор транзакции
     * @param transactionType Тип транзакции
     * @param transactionAmount Сумма транзакции
     */
    void saveTransactionId(int playerId, String transactionId, String transactionType, double transactionAmount);

    /**
     * Обновляет информацию об игроке.
     * @param user Объект User, содержащий информацию об игроке
     */
    void updatePlayer(User user);
}