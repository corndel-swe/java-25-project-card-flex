package org.project.cardflex.Repository;

import org.project.cardflex.DB;
import org.project.cardflex.Model.Cards;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardRepository {

//   Display Credit Card Type to Users
    public static Cards newCreditCard (int userId, String cardName) throws SQLException {
        var query = "INSERT INTO Cards (accountNumber, creditLimit, APR, refreshDate, cardName, balance), VALUES (? ? ? ? ? ?)";
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query))
             {
                 Random r = new Random ();
                 float APR = 0;
                 int creditLimit = 0;
                 if (Boolean.parseBoolean(cardName ="Gold".toLowerCase())){
                 APR = 10.70F;
                 creditLimit = 5000;}
             else if (Boolean.parseBoolean(cardName = "Platinum".toLowerCase())) {
                 APR = 15.10F;
                 creditLimit = 15000;}

             else {
                     APR = 18.00F;
                     creditLimit = 25000;}

                int accountNumber =  r.nextInt(10000,99999);
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
    public static List<Cards> findCardsByUserID () throws SQLException {
        var query = "SELECT * FROM Cards WHERE userID = ?";
        try (var con = DB.getConnection();
             var stmt = con.createStatement();
             var rs = stmt.executeQuery(query)) {

            var cards = new ArrayList<Cards>();
            while (rs.next()) {
                var id = rs.getInt("id");
                var userId = rs.getInt("userId");
                var accountNumber = rs.getInt("accountNumber");
                var creditLimit = rs.getFloat("creditLimit");
                var APR = rs.getFloat("APR");
                var startDate = rs.getString("startDate");
                var refreshDate = rs.getString("refreshDate");
                var cardName = rs.getString("cardName");


                cards.add(new Cards(id, userId, accountNumber, creditLimit, APR, startDate, refreshDate, cardName));
            }
            return cards;

        }
//Test

    }



    public static Float viewCardBalance (int id) throws  SQLException{

        var query = "SELECT balance FROM cards WHERE id = ?";
        try (var con = DB.getConnection();
            var stmt = con.prepareStatement(query)){

            stmt.setInt(1,id);

            try (var rs = stmt.executeQuery();){
                var balance = rs.getFloat("balance"); //5 index

                return balance;
            }
        }

    }

    public static Float viewAPR(int id) throws SQLException{
        var query = "SELECT apr FROM cards WHERE id = ?";
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query)){

            stmt.setInt(1,id);

            try (var rs = stmt.executeQuery();){
                var apr = rs.getFloat("apr"); //5 index

                return apr;
            }
        }
    }

    public static void applyAPR (int id) throws SQLException{

        float balance = viewCardBalance(id); //balance
        float apr = viewAPR(id); //apr

        //apr 20
        //apr to apply 0.2 + 1
        //apr // 100 + 1

        float aprToUse = apr / 100 + 1;

        balance *= aprToUse;
        //decided to store/log in transactions from an admin user

    }






    public static void main(String[] args) throws SQLException {
        System.out.println(viewCardBalance(1));
        System.out.println(20.00 / 100.00 + 1 );
    }

}



