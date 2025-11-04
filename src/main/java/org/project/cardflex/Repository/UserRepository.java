package org.project.cardflex.Repository;

import org.project.cardflex.DB;
import org.project.cardflex.Model.User;

import java.sql.SQLException;

public class UserRepository {

    // This method will check the User exists in the database, therefore returning a User to login //
    public static User checkUsername(String username) throws SQLException {
        var query = "SELECT * FROM users WHERE username = ? ";

        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query)) {

            stmt.setString(1, username);

            try (var rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                var id = rs.getInt("id");
                var userName = rs.getString("username");
                var totalBalance = rs.getFloat("total_balance");
                System.out.println(id + userName + totalBalance);

                return new User (id, userName, totalBalance);

    }

}
    }
    //Method will update the users total balance sourced from Users table
    public static float updateTotalBalance(String username) throws SQLException{
        var query = "SELECT total_balance from users where username = ?";


        try (var con = DB.getConnection();
            var stmt = con.prepareStatement(query)) {

            stmt.setString(1, username);

            try (var rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Float.parseFloat(null);
                }
                var total_balance = rs.getFloat("total_balance");

                return total_balance;
            }

        }

    }

//    public static void main(String[] args){
//        try {
//            System.out.println(checkUsername("MashFetchum"));
//            System.out.println("hi");
//        } catch (SQLException e) {
//            System.out.println("uh oh");
//            throw new RuntimeException(e);
//        }
//    }

}
