package com.mateuszwiater.csc435.model;

/*
CREATE TABLE USERS (
    ID int NOT NULL AUTO_INCREMENT,
    USERNAME VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    APIKEY UUID NOT NULL,
    PRIMARY KEY (ID)
);
 */

import com.mateuszwiater.csc435.db.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import static com.mateuszwiater.csc435.model.Model.Status.*;

public class User {
    private static Logger logger = LoggerFactory.getLogger(User.class);
    private final String userName, apiKey;
    private String password;

    public static Model<User> getUser(final UUID apiKey) {
        final String sql = String.format("SELECT (USERNAME, PASSWORD, APIKEY) " +
                "FROM USERS " +
                "WHERE APIKEY='%s';", apiKey);

        return getUser(sql);
    }

    public static Model<User> getUser(final String userName, final String password) {
        final String sql = String.format("SELECT (USERNAME, PASSWORD, APIKEY) " +
                "FROM USERS " +
                "WHERE USERNAME='%s' " +
                "AND PASSWORD='%s';", userName, password);

        return getUser(sql);
    }

    private static Model<User> getUser(final String sql) {
        try (final Statement stmt = DatabaseConnector.getConnection().createStatement();
             final ResultSet rs = stmt.executeQuery(sql)) {

            // Return an error if the result set is empty.
            if(!rs.next()) {
                return new Model<>(FAIL,"No User Found With That Information.");
            }

            // Return the user from the database.
            final String apiKey = rs.getString("APIKEY");
            final String userName = rs.getString("USERNAME");
            final String password = rs.getString("PASSWORD");
            return new Model<>(new User(userName, password, apiKey));

        } catch (SQLException e) {
            logger.error("SQL Error when retrieving User", e);
            return new Model<>(FAIL,"Internal Server Error!");
        }
    }

    private User(final String userName, final String password, final String apiKey) {
        this.apiKey = apiKey;
        this.userName = userName;
        this.password = password;
    }

    public Model<User> delete() {
        final String sql = String.format("DELETE FROM USERS WHERE APIKEY='%s'", apiKey);

        try (final Statement stmt = DatabaseConnector.getConnection().createStatement();
             final ResultSet rs = stmt.executeQuery(sql)) {
            return new Model<>(OK,"User Removed.");
        } catch (SQLException e) {
            logger.error("SQL Error when deleting User", e);
            return new Model<>(FAIL,"Internal Server Error!");
        }
    }

    public Model<User> setPassword(final String password) {
        final String sql = String.format("UPDATE USERS SET PASSWORD='%s' WHERE APIKEY='%s'", password, apiKey);

        try (final Statement stmt = DatabaseConnector.getConnection().createStatement();
             final ResultSet rs = stmt.executeQuery(sql)) {
            return new Model<>(OK,"Password Changed.", this);
        } catch (SQLException e) {
            logger.error("SQL Error when deleting User", e);
            return new Model<>(FAIL,"Internal Server Error!");
        }
    }
}
