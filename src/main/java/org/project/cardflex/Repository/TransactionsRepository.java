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
    public static void addTransaction (Transactions transaction) throws SQLException {
        var query = "INSERT INTO transactions (senders_card_id, senders_username, senders_account_number, recipient_username, recipient_account_number, amount, description, transaction_date) VALUES (?,?,?,?,?,?,?,?) ";
        try ( var con = DB.getConnection();
              var stmt = con.prepareStatement(query)){
            stmt.setInt(1, transaction.getSendersCardId());
            stmt.setString(2, transaction.getSendersUsername());
            stmt.setInt(3, transaction.getSendersAccNum());
            stmt.setString(4, transaction.getRecipientUsername());
            stmt.setInt(5, transaction.getRecipientAccNum());
            stmt.setFloat(6, transaction.getAmount());
            stmt.setString(7, transaction.getDescription());
            stmt.setString(8, transaction.getTransactionDate());

            stmt.executeUpdate();
        }


    }

    public static void main(String[] args) throws SQLException {
        TransactionsRepository.addTransaction(new Transactions(1, 1,"Cardflex",  123456,"user", 125, "Test transaction", "01-01-0000"));
    }
}
