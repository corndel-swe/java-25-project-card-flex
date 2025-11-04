package org.project.cardflex.Repository;

import org.project.cardflex.Model.Cards;

import javax.smartcardio.Card;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardRepository {

//   Display Credit Card Type to Users
    public static Cards newCreditCard (int userId, String cardName) throws SQLException {
        var query = "INSERT INTO Cards (userId, accountNumber, creditLimit, APR, startDate, refreshDate, cardName), VALUES (? ? ? ? ? ?)";
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query))
             {
                 Random r = new Random ();
                 float APR = 0;
                 int creditLimit = 0;
                 if (Boolean.parseBoolean(cardName ="Gold".toLowerCase())){
                 APR = 10.00F;
                 creditLimit = 5000;}
             else if (Boolean.parseBoolean(cardName = "Platinum".toLowerCase())) {
                 APR = 25.00F;
                 creditLimit = 15000;}

             else {
                     APR = 40.00F;
                     creditLimit = 2500;}


                int accountNumber =   stmt.setInt(1, r.nextInt(10000)); // Check to see if it's unique
                stmt.setFloat(3, creditLimit);
                stmt.setFloat(4, APR);
                var startDate = stmt.setString(5, "startDate");
                var refreshDate = stmt.setString(6, "refreshDate");

                try (var rs = stmt.executeQuery();) {
                   startDate =  rs.getstring("startDate");
                   refreshDate = rs.getString("refreshDate");
                }

                return new Cards(userId, accountNumber, creditLimit, APR, startDate, refreshDate, cardName); // METHOD OVERLOADING REQUIRED
             }


        }
    // Locating Exisiting Cards Based off User ID
    public static List<Cards> findCardsByUserID () throws SQLException {
        var query = "SELECT * FROM Cards WHERE userID = ?";
        try (var con = DB.getConnection();
             var stmt = con.createStatement();
             var rs = stmt.executeQuery(query)) {

            var cards = new ArrayList<Cards>();
            while (rs.next()) {
                var id = rs.getInt ("id");
                var userId = rs.getInt("userId");
                var accountNumber= rs.getInt("accountNumber");
                var creditLimit = rs.getfloat("creditLimit");
                var APR = rs.getfloat("APR");
                var startDate = rs.getstring("startDate");
                var refreshDate = rs.getString("refreshDate");
                var cardName = rs.getString("cardName"); }


            cards.add(new Cards(id,userID,accountNumber, creditLimit, APR, startDate, refreshDate, cardName));
        }
        return cards;
        //TEST


    }
}



