package org.project.cardflex.Repository;

import org.project.cardflex.DB;
import org.project.cardflex.Model.Cards;
import org.project.cardflex.Model.Transactions;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    public static float updateBalance (float balance, int id) throws SQLException{
        var query = "UPDATE cards SET balance = ? WHERE id = ?";

        try (var con = DB.getConnection();
            var stmt = con.prepareStatement(query);)
        {
            stmt.setFloat(1, balance);
            stmt.setInt(2, id);


            try (var rs = stmt.executeQuery();){
                var newBalance = rs.getFloat("balance");

                return newBalance;
            }
        }

    };


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

        Date date = new Date();
        SimpleDateFormat formatpattern = new SimpleDateFormat("dd-MM-yyyy");
        var currentDate = formatpattern.format(date);
        System.out.println(currentDate);


        //apr 20
        //apr to apply 0.2 + 1
        //apr // 100 + 1

        float aprToUse = apr / 100 + 1;

        float interest = balance * aprToUse;

        // connect to Database and Execute Query

        var recipientsAccNum = "Select account_number FROM cards WHERE id = "+id;


        //var query = recipientsAccNum + ";" + recipientsUserID + ";" + recipientsUserName;
        int RecipientAN = 0;
        int RecipientUserID = 0;
        try( var con = DB.getConnection();
            var stmt = con.createStatement();
            var rs = stmt.executeQuery(recipientsAccNum)) {
            System.out.println("Executed query 1");
            RecipientAN = rs.getInt("account_number");
            System.out.println("Got acc num");
        }

        var recipientsUserID = "SELECT user_id FROM cards WHERE id = "+id;

            try (var con2 = DB.getConnection();
                 var stmt2 = con2.createStatement();
                    var rs2 = stmt2.executeQuery(recipientsUserID)) {
                System.out.println("Executed query 2");

                RecipientUserID = rs2.getInt("user_id");
                System.out.println("Got user id");


            }
            var recipientsUserName = "Select username FROM users WHERE id = " + RecipientUserID;
            Transactions transactions = null;
            try (
                    var con3 = DB.getConnection();
                    var stmt3 = con3.createStatement();
                    var rs3 = stmt3.executeQuery(recipientsUserName)) {
                        System.out.println("Executed query 3");
                        var RecipientUserName = rs3.getString("username");

                        System.out.println("Got username");
                        System.out.println(RecipientAN + " " + RecipientUserName + " " + interest + " " + "Interest applied"+ " " + currentDate);
                        transactions = new Transactions(1, 1, "cardFlex", RecipientAN, RecipientUserName, interest, "Interest Applied", currentDate);




                    }

            TransactionsRepository.addTransaction(transactions);
            updateBalance((balance + interest), id);
    }





// Testing the method
    public static void main(String[] args) throws SQLException {
        var Cardid = 1;
        System.out.println(viewCardBalance(Cardid));
        System.out.println(20.00 / 100.00 + 1 );
        System.out.println(viewAPR(Cardid));
        applyAPR(Cardid);
    }

}



