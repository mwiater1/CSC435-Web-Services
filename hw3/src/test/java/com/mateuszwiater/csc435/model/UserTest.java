package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class UserTest {

    @Before
    public void before() throws SQLException {
        // Create the users
        DatabaseConnector.runQuery("CREATE TABLE USERS (" +
                "USERNAME VARCHAR(255) NOT NULL," +
                "PASSWORD VARCHAR(255) NOT NULL," +
                "APIKEY UUID NOT NULL," +
                "PRIMARY KEY (USERNAME));");

        // Create the test user.
        new User("test","abc123").save();
    }

    @After
    public void after() throws SQLException {
        // Drop the users table
        DatabaseConnector.runQuery("DROP TABLE USERS;");
    }

    @Test
    public void getUserByPasswordAndName() throws SQLException {
        // Get the test user from the database
        final Optional<User> user = User.getUser("test", "abc123");

        // Check if the user returned is the one requested
        assertThat(user.isPresent(), is(equalTo(true)));
        assertThat(user.get().getUserName(), is(equalTo("test")));
        assertThat(user.get().getPassword(), is(equalTo("abc123")));
    }

    @Test
    public void getUserByApiKey() throws SQLException {
        // Get the test user from the database
        Optional<User> user = User.getUser("test", "abc123");

        // Make sure that a user was returned
        assertThat(user.isPresent(), is(equalTo(true)));

        // Grab the user again but with the api key
        final String apiKey = user.get().getApiKey();
        user = User.getUser(UUID.fromString(apiKey));

        // Check if the user returned is the one requested
        assertThat(user.isPresent(), is(equalTo(true)));
        assertThat(user.get().getUserName(), is(equalTo("test")));
        assertThat(user.get().getPassword(), is(equalTo("abc123")));
        assertThat(user.get().getApiKey(), is(equalTo(apiKey)));
    }

    @Test
    public void deleteUser() throws SQLException {
        // Get the test user from the database
        Optional<User> user = User.getUser("test", "abc123");

        // Make sure that a user was returned
        assertThat(user.isPresent(), is(equalTo(true)));

        // Delete the user
        user.get().delete();

        // Try to get the user again
        user = User.getUser("test", "abc123");

        // Make sure no user was returned
        assertThat(user.isPresent(), is(equalTo(false)));

    }

    @Test
    public void changeUserNameAndPassword() throws SQLException {
        // Get the test user from the database
        Optional<User> user = User.getUser("test", "abc123");

        // Make sure that a user was returned
        assertThat(user.isPresent(), is(equalTo(true)));

        // Change the username and password
        user.get().setUserName("newName");
        user.get().setPassword("newPassword");
        user.get().save();

        // Make sure the old user does not exist
        user = User.getUser("test", "abc123");
        assertThat(user.isPresent(), is(equalTo(false)));

        // Make sure the new user exists
        user = User.getUser("newName", "newPassword");
        assertThat(user.isPresent(), is(equalTo(true)));
        assertThat(user.get().getUserName(), is(equalTo("newName")));
        assertThat(user.get().getPassword(), is(equalTo("newPassword")));
    }
}
