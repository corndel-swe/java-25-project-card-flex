package org.project.cardflex.Repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.cardflex.Model.User;
import org.project.cardflex.db.TestDB;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private static TestDB dbHelper;
    private UserRepository userRepository;

    @BeforeAll
    static void setupDb() throws Exception {
        dbHelper = new TestDB();
        dbHelper.migrate();
    }

    @BeforeEach
    void resetDb() {
        dbHelper.cleanAndMigrate();
        this.userRepository = new UserRepository(dbHelper);
    }

    @AfterAll
    static void tearDownDb() {
        dbHelper.shutdown();
    }

    @Test
    void testCheckUsername() throws SQLException {
        String name = "TrustFundBaby";
        int id = 1;
        float balance = 100f;

        User retrievedUser = userRepository.checkUsername(name);

        assertEquals(name, retrievedUser.getUsername());
        assertEquals(id, retrievedUser.getId());
        assertEquals(balance, retrievedUser.getTotalBalance());
    }
}