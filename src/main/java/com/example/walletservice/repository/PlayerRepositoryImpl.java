package com.example.walletservice.repository;

import com.example.walletservice.database.ConnectionManager;
import com.example.walletservice.model.User;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository {
    private static final PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();

    public static PlayerRepositoryImpl getInstance()  {
        return playerRepository;
    }


    @Override
    public void registerPlayer(String username, String password) {
        String sql = "INSERT INTO wallet.players (username, password, balance) VALUES (?, ?, 0)";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            System.out.println("Вы успешно зарегистрировались, " + username + "!");
        } catch (SQLException e) {
            System.out.println("Ошибка при регистрации игрока: " + e.getMessage());
        }
    }

    @Override
    public boolean authenticatePlayer(String username, String password) {
        String sql = "SELECT COUNT(*) FROM wallet.players WHERE username = ? AND password = ?";
        try ( Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count  = resultSet.getInt(1);
                boolean isAuthenticated = count > 0;
                if (isAuthenticated) {
                    System.out.println("Авторизация успешна!");
                } else {
                    System.out.println("Неверный логин или пароль.");
                }
                return isAuthenticated;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка аунтификации игрока: " + e.getMessage());
        } return false;
    }

    @Override
    public User get(String username) {
        String sql = "SELECT * FROM wallet.players WHERE username = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int playerId = resultSet.getInt("id");
                double balance = resultSet.getDouble("balance");
                String password = resultSet.getString("password");

                Set<String> transactionIds = new HashSet<>();
                Map<String, String> transactionTypes = new HashMap<>();

                String transactionSql = "SELECT * FROM wallet.transaction_ids WHERE player_id = ?";
                try (PreparedStatement transactionStatement = connection.prepareStatement(transactionSql)) {
                    transactionStatement.setInt(1, playerId);
                    ResultSet transactionResultSet = transactionStatement.executeQuery();

                    while (transactionResultSet.next()) {
                        String transactionId = transactionResultSet.getString("transaction_id");
                        String transactionType = transactionResultSet.getString("transaction_type");

                        transactionIds.add(transactionId);
                        transactionTypes.put(transactionId, transactionType);
                    }
                }

                return new User(playerId, username, password, balance, transactionIds, transactionTypes);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении игрока: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void updatePlayer(User user) {
        String sql = "UPDATE wallet.players SET balance = ? WHERE username = ?";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, user.getBalance());
            statement.setString(2, user.getUsername());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении игрока: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM wallet.players");
            statement.executeUpdate("DELETE FROM wallet.audit_logs");
            statement.executeUpdate("DELETE FROM wallet.transaction_ids");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса DELETE: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean isPlayerExists(String username, String password) {
        String userIdSql = "SELECT COUNT(*) FROM wallet.players WHERE username = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(userIdSql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                boolean doesUserExist = count > 0;
                if (doesUserExist) {
                    System.out.println("Пользователь с именем " + username + " уже существует.");
                }
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Ошибка проверки существования игрока: " + ex.getMessage());
        }

        return false;
    }
}