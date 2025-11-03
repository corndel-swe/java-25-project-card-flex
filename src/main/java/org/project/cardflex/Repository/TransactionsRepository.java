package org.project.cardflex.Repository;

import org.project.cardflex.Model.Transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionsRepository {
    public static List<Transactions> findById (int id) throws SQLException {
        var query = "SELECT * FROM TRANSACTIONS WHERE cardId = ? ORDER BY transactionDate DESC";
        try (Connection con = DB.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Transactions> transactions = new ArrayList<>();
                while (resultSet.next()) {

                    int transactionId = (resultSet.getInt("id"));
                    int sendersAccountNumber = (resultSet.getInt("sendersAccountNumber"));
                    int sendersCardId = (resultSet.getInt("sendersCardId"));
                    String recepientUsername = (resultSet.getString("recepientUsername"));
                    int recepientAccountNumber = (resultSet.getInt("recepientAccountNumber"));
                    String sendersUsername = (resultSet.getString("sendersUsername"));
                    float amount = (resultSet.getFloat("amount"));
                    String description = (resultSet.getString("description"));
                    String transactionDate = resultSet.getString("transactionDate");

                    transactions.add(new Transactions(transactionId, sendersAccountNumber, sendersCardId, recepientUsername, recepientAccountNumber, sendersUsername, amount, description, transactionDate));
                }

                return transactions;
            }

        }
    }
}
