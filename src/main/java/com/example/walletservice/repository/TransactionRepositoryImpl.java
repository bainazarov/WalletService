package com.example.walletservice.repository;

import com.example.walletservice.database.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRepositoryImpl implements TransactionRepository{

    @Override
    public void saveTransactionId(int playerId, String transactionId, String transactionType, double transactionAmount) {
        String sql = "INSERT INTO wallet.transaction_ids (transaction_id, transaction_type, player_id, amount) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transactionId);
            statement.setString(2, transactionType);
            statement.setInt(3, playerId);
            statement.setDouble(4, transactionAmount);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении идентификатора транзакции: " + e.getMessage());
        }
    }
}
