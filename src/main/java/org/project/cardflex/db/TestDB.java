package org.project.cardflex.db;

import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDB implements DBConnection {

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";
    private final Path dbFile;
    private final String jdbcUrl;
    private Flyway flyway;

    public TestDB() throws IOException {
        this.dbFile = Files.createTempFile("test-db-", ".sqlite");
        this.jdbcUrl = JDBC_URL_PREFIX + dbFile.toString();
        this.flyway = Flyway.configure()
                .dataSource(this.jdbcUrl, null, null) // SQLite doesn't need username/password
                .cleanDisabled(false)
                .load();
    }

    public void migrate() {
        flyway.migrate();
    }

    public void cleanAndMigrate() {
        flyway.clean();
        flyway.migrate();
    }

    public void shutdown() {
        try {
            Files.deleteIfExists(dbFile);
        } catch (IOException e) {
            // Handle error, e.g., log it
            e.printStackTrace();
        }
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getJdbcUrl());
    }
}
