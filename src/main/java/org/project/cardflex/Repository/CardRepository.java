package org.project.cardflex.Repository;

import org.project.cardflex.DB;
import org.project.cardflex.Model.Cards;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardRepository {

    //   Display Credit Card Type to Users
    public static Cards newCreditCard(int userId, String cardName) throws SQLException {
        var query = "INSERT INTO Cards (account_number, credit_limit, apr, refresh_date, card_name, balance), VALUES (? ? ? ? ? ?)";
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query)) {
            Random r = new Random();
            float APR = 0;
            int creditLimit = 0;
            if (Boolean.parseBoolean(cardName = "Gold".toLowerCase())) {
                APR = 10.70F;
                creditLimit = 5000;
            } else if (Boolean.parseBoolean(cardName = "Platinum".toLowerCase())) {
                APR = 15.10F;
                creditLimit = 15000;
            } else {
                APR = 18.00F;
                creditLimit = 25000;
            }

            int accountNumber = r.nextInt(10000);
            stmt.setInt(1, accountNumber); // Check to see if it's unique
            stmt.setFloat(2, creditLimit);
            stmt.setFloat(3, APR);
            stmt.setString(4, "1");
            stmt.setString(5, cardName);
            stmt.setInt(6, 0);
            try (var rs = stmt.executeQuery();) {
                var accountNum = rs.getInt(3);
                var creditLim = rs.getInt(4);
                var balance = rs.getInt(5);
                var apr = rs.getFloat(6);
                var startDate = rs.getString(7);
                return new Cards(accountNum, creditLim, apr, cardName, startDate, balance);
            }

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
                    var balance = rs.getInt("balance");
                    cards.add(new Cards(userId, accountNumber, creditLimit, APR, cardName, startDate, refreshDate, balance));
                }
                return cards;

            }
        }
    }

    // check balance on card

    public static int checkBalance(int id, int userId, int balance) throws SQLException {

        var balanceQuery = "SELECT balance FROM cards WHERE id = ?";

        try (
                var connection = DB.getConnection();
                var statement = connection.prepareStatement(balanceQuery);
                var rs = statement.executeQuery();

        ) {

            var cardBalance = rs.getInt("balance");
            statement.setInt(1, id);
            statement.setInt(2, userId);
            statement.setInt(3, balance);
            statement.executeUpdate();

            return cardBalance;
        }
    }

    // create method to delete card

    public static void deleteCard(int id, int userId) throws SQLException {

        var deleteQuery = "DELETE id FROM cards WHERE id = ?  AND balance = 0";

        try (
                var connection = DB.getConnection();
                var statement = connection.prepareStatement(deleteQuery)

                ) {

            statement.setInt(1, id);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }

    }
}



