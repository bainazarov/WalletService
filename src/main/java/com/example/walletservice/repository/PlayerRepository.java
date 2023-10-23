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
    User get(String username);

    /**
     * Обновляет информацию об игроке.
     * @param user Объект User, содержащий информацию об игроке
     */
    void updatePlayer(User user);

    /**
     * Удаляет все записи из таблицы wallet.players, wallet.audit_logs и wallet.transaction_ids.
     *
     * @throws RuntimeException если произошла ошибка при выполнении SQL-запроса DELETE.
     */
    void deleteAll();

    /**
     * Проверяет существование игрока с указанным именем и паролем в таблице wallet.players.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return true, если игрок существует, иначе false
     */
    boolean isPlayerExists(String username, String password);
}