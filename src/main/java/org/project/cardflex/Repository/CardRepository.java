package org.project.cardflex.Repository;

import org.project.cardflex.DB;
import org.project.cardflex.Model.Cards;
import org.project.cardflex.Model.Statement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.project.cardflex.Repository.TransactionsRepository.findById;

public class CardRepository {
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
    //Call method after any addition of transactions or before any use of balance
    public static void updateBalance(int id) throws SQLException{
        var query = "UPDATE cards SET balance = (SELECT SUM(amount) FROM transactions INNER JOIN cards_transactions ON transactions.id = cards_transactions.transactions_id WHERE cards.id = ?) WHERE cards.id = ?";
        try (Connection con = DB.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }
    }
}
