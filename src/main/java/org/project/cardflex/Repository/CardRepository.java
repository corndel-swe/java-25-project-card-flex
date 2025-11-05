package org.project.cardflex.Repository;

import org.project.cardflex.DB;
import org.project.cardflex.Model.Cards;
import org.project.cardflex.Model.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardRepository {

    //   Display Credit Card Type to Users
    public static void newCreditCard(int userId, String cardName) throws SQLException {
        var query = "INSERT INTO cards (account_number, credit_limit, apr, refresh_date, card_name, balance, user_id ) VALUES (?, ?,?,?,?,?,?)";
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query)) {
            Random r = new Random();
            float APR = 0;
            int creditLimit = 0;
            cardName.toUpperCase(); // Ensures that cardName is consistent
            if (cardName.equals("GOLD")) {
                APR = 10.70F;
                creditLimit = 5000;
            } else if (cardName.equals("PLATINUM")) {
                APR = 15.10F;
                creditLimit = 15000;
            } else {
                APR = 18.00F;
                creditLimit = 25000;
            }
            int accountNumber = r.nextInt(100000, 999999);

            stmt.setInt(1, accountNumber); // Check to see if it's unique
            stmt.setFloat(2, creditLimit);
            stmt.setFloat(3, APR);
            stmt.setString(4, "1");
            stmt.setString(5, cardName);
            stmt.setFloat(6, 0);
            stmt.setInt(7, userId);
            stmt.executeUpdate();

        }
    }

    // Locating Existing Cards Based off User ID
    public static List<Cards> findCardsByUserID(int userId) throws SQLException {
        var query = "SELECT * FROM Cards WHERE user_id = ?";
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (var rs = stmt.executeQuery()) {

                var cards = new ArrayList<Cards>();
                while (rs.next()) {
                    var accountNumber = rs.getInt("account_number");
                    var creditLimit = rs.getFloat("credit_limit");
                    var APR = rs.getFloat("apr");
                    var startDate = rs.getString("start_date");
                    var refreshDate = rs.getString("refresh_date");
                    var cardName = rs.getString("card_name");
                    var balance = rs.getFloat("balance");
                    cards.add(new Cards(userId, accountNumber, creditLimit, APR, cardName, startDate, refreshDate, balance));
                }
                return cards;

            }
        }
    }

    // create method to delete card if there's no remaining balance to be paid off on the card
    public static void deleteCard(int cardId) throws SQLException {

        var deleteQuery = "DELETE FROM cards WHERE id = ? AND balance = 0";

        try (   var connection = DB.getConnection();
                var statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, cardId);
            statement.executeUpdate();
        }

    }


    public static List<Statement> buildCardStatement(int id) throws SQLException {


        var query = "SELECT cards.account_number, cards.balance, cards.credit_limit, cards.id FROM cards WHERE cards.id = ?";

        List<Statement> statement = new ArrayList<>();

        try (Connection con = DB.getConnection();

             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int cardId = (resultSet.getInt("id"));
                int accountNumber = (resultSet.getInt("account_number"));
                updateBalance(cardId);
                float balance = (resultSet.getFloat("balance"));
                float creditLimit = (resultSet.getFloat("credit_limit"));
                float remainingBalance = creditLimit - balance;
                statement.add(new Statement(accountNumber, balance, remainingBalance));

                return statement;
            }
        }
    }



    public static void updateBalance(int id) throws SQLException{
        var query = "UPDATE cards SET balance = (SELECT SUM(amount) FROM transactions INNER JOIN cards_transactions ON transactions.id = cards_transactions.transactions_id WHERE cards.id = ?) WHERE cards.id = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1,id);
        }
    }
}


