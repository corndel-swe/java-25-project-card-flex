package org.project.cardflex.Repository;


import org.project.cardflex.Model.Cards;
import org.project.cardflex.Model.User;
import org.project.cardflex.db.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final DBConnection dbConnection;

    public UserRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // This method will check the User exists in the database, therefore returning a User to login //
    public User checkUsername(String username) throws SQLException {
        var query = "SELECT * FROM users WHERE username = ? ";

        try (var con = dbConnection.getConnection();
             var stmt = con.prepareStatement(query)) {

            stmt.setString(1, username);

            try (var rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                var id = rs.getInt("id");
                var userName = rs.getString("username");
                var totalBalance = rs.getFloat("total_balance");

                return new User(id, userName, totalBalance);
            }

        }
    }

    //returns a list of all cardids relating to a user EB
    public List<Cards> getAllCardsByUserId(int user_id) throws SQLException {

        var query = "SELECT users_cards.card_id, cards.* " +
                "FROM users_cards INNER JOIN cards ON cards.user_id = users_cards.user_id " +
                "WHERE users_cards.user_id = ?";

        try (var con = dbConnection.getConnection();
             var stmt = con.prepareStatement(query)) {

            stmt.setInt(1, user_id);
            List<Cards> cards = new ArrayList<>();

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    var cardId = rs.getInt("id");
                    var userId = rs.getInt("user_id");
                    var accountNumber = rs.getInt("account_number");
                    var cardName = rs.getString("card_name");
                    var creditLimit = rs.getFloat("credit_limit");
                    var cardBalance = rs.getFloat("balance");
                    var startDate = rs.getString("start_date");
                    var refreshDate = rs.getInt("refresh_date");
                    var apr = rs.getFloat("apr");

                    cards.add(new Cards(cardId, userId, accountNumber, creditLimit, apr, startDate, refreshDate, cardName, cardBalance ));
                }
            }
            return cards;
        }

    }


    //Method will find the users total balance sourced from Users table
    public float findTotalBalance(int userId) throws SQLException {
        var query = "SELECT total_balance from users where id = ?";

        try (var con = dbConnection.getConnection();
             var stmt = con.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (var rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return 0f;
                }

                return rs.getFloat("total_balance");
            }

        }

    }



}
