package org.project.cardflex.Repository;

import org.project.cardflex.DB;
import org.project.cardflex.Model.Transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionsRepository {
    public static List<Transactions> findById (int id) throws SQLException {
        var query = "SELECT * FROM transactions INNER JOIN cards_transactions ON transactions.id = cards_transactions.transactions_id INNER JOIN cards on cards.id = cards_transactions.card_id WHERE card_id = ? ORDER BY transaction_date DESC";
        try (Connection con = DB.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Transactions> transactions = new ArrayList<>();
                while (resultSet.next()) {

                    int transactionId = (resultSet.getInt("id"));
                    int sendersAccountNumber = (resultSet.getInt("senders_account_number"));
                    int sendersCardId = (resultSet.getInt("senders_card_id"));
                    String recipientUsername = (resultSet.getString("recipient_username"));
                    int recipientAccountNumber = (resultSet.getInt("recipient_account_number"));
                    String sendersUsername = (resultSet.getString("senders_username"));
                    float amount = (resultSet.getFloat("amount"));
                    String description = (resultSet.getString("description"));
                    String transactionDate = resultSet.getString("transaction_date");

                    transactions.add(new Transactions(transactionId, sendersAccountNumber, sendersCardId, recipientUsername, recipientAccountNumber, sendersUsername, amount, description, transactionDate));
                }

                return transactions;
            }

        }
    }
}
