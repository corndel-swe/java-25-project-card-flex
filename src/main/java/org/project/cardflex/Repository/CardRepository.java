package org.project.cardflex.Repository;

import org.project.cardflex.DB;
import org.project.cardflex.Model.Cards;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardRepository {

    //   Display Credit Card Type to Users
    public static void newCreditCard(int userId, String cardName) throws SQLException {
        var query = "INSERT INTO cards (account_number, credit_limit, apr, refresh_date, card_name, balance, user_id ) VALUES (?,?,?,?,?,?,?)";
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query)) {
            Random r = new Random();
            float APR = 0;
            int creditLimit = 0;
            cardName.toUpperCase();
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
}



