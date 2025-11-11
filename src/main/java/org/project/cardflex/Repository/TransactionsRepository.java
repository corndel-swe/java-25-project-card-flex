package org.project.cardflex.Repository;

import org.project.cardflex.db.DB;
import org.project.cardflex.Model.Transactions;
import org.project.cardflex.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionsRepository {

    private final DBConnection DB;

    public TransactionsRepository(DBConnection DB) {
        this.DB = DB;
    }

    public List<Transactions> findById (int id) throws SQLException {
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
                    String recipientUsername = (resultSet.getString("recipient_Username"));
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

    public List<Transactions> findMonthlyById (int id) throws SQLException {
        var query = "SELECT * FROM transactions INNER JOIN cards_transactions ON transactions.id = cards_transactions.transactions_id INNER JOIN cards ON cards.id = cards_transactions.card_id WHERE card_id = ? AND transaction_date BETWEEN date('now', '-28 days') AND date('now') ORDER BY transaction_date DESC;";
        try (Connection con = DB.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Transactions> transactions = new ArrayList<>();
                while (resultSet.next()) {

                    int transactionId = (resultSet.getInt("id"));
                    int sendersAccountNumber = (resultSet.getInt("senders_account_number"));
                    int sendersCardId = (resultSet.getInt("senders_card_id"));
                    String recipientUsername = (resultSet.getString("recipient_Username"));
                    int recipientAccountNumber = (resultSet.getInt("recipient_account_number"));
                    String sendersUsername = (resultSet.getString("senders_username"));
                    float amount = (resultSet.getFloat("amount"));
                    String description = (resultSet.getString("description"));
                    String transactionDate = resultSet.getString("transactionDate");

                    transactions.add(new Transactions(transactionId, sendersAccountNumber, sendersCardId, recipientUsername, recipientAccountNumber, sendersUsername, amount, description, transactionDate));
                }

                return transactions;
            }

        }
    }
    public void addTransaction (Transactions transaction) throws SQLException {
        var query = "INSERT INTO transactions (senders_card_id, senders_username, senders_account_number, recipient_username, recipient_account_number, amount, description, transaction_date)  VALUES (?,?,?,?,?,ROUND(?,2),?,?) ";
        try ( var con = DB.getConnection();
              var stmt = con.prepareStatement(query)){
            stmt.setInt(1, transaction.getSendersCardId());
            stmt.setString(2, transaction.getSendersUsername());
            stmt.setInt(3, transaction.getSendersAccNum());
            stmt.setString(4, transaction.getRecipientUsername());
            stmt.setInt(5, transaction.getRecipientAccNum());

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            var amount = Float.parseFloat((df.format(transaction.getAmount())));

            stmt.setFloat(6, amount);
            stmt.setString(7, transaction.getDescription());
            stmt.setString(8, transaction.getTransactionDate());

            stmt.executeUpdate();
        }


    }

}
