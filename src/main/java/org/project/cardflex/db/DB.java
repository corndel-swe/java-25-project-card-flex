package org.project.cardflex.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB implements DBConnection {

    public static final String dbUrl = "jdbc:sqlite:card-flex.db";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }

}
