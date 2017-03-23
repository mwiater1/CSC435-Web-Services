package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;
import com.mateuszwiater.csc435.db.SqlResponse;
import com.mateuszwiater.csc435.db.SqlStatus;

import java.util.List;
import java.util.UUID;

import static com.mateuszwiater.csc435.util.HttpStatus.*;

public class User {
    private String userName, password, apiKey;

    public static ModelResponse<User> getUser(final UUID apiKey) {
        final String query = String.format("SELECT * " +
                "FROM USERS " +
                "WHERE APIKEY='%s';", apiKey);

        return getUser(query);
    }

    public static ModelResponse<User> getUser(final String userName, final String password) {
        final String query = String.format("SELECT * " +
                "FROM USERS " +
                "WHERE USERNAME='%s' " +
                "AND PASSWORD='%s';", userName, password);

        return getUser(query);
    }

    private static ModelResponse<User> getUser(final String sql) {
        final SqlResponse res = DatabaseConnector.runQuery(sql);

        // Make sure the query did not error out
        if (res.getStatus() == SqlStatus.OK) {
            // Check to see if the query returned a user
            if (res.getData().isPresent()) {
                final List<String> row = res.getData().get().get(0);
                final User user = new User(row.get(0), row.get(1), row.get(2));
                return new ModelResponse<>(OK, user, "");
            } else {
                return new ModelResponse<>(NOT_FOUND, null, "User not found.");
            }
        } else {
            return new ModelResponse<>(INTERNAL_SERVER_ERROR, null, "");
        }
    }

    public static ModelResponse<User> createUser(final String userName, final String password) {
        final String apiKey = UUID.randomUUID().toString();
        final String query = String.format("INSERT INTO USERS (USERNAME,PASSWORD,APIKEY) VALUES ('%s','%s','%s')",
                userName, password, apiKey);

        final SqlResponse res = DatabaseConnector.runQuery(query);

        if (res.getStatus() == SqlStatus.OK) {
            return new ModelResponse<>(OK, new User(userName, password, apiKey), "Successfully created user.");
        } else if (res.getStatus() == SqlStatus.PRIMARY_KEY_VIOLATION) {
            return new ModelResponse<>(CONFLICT, null, "Username '" + userName + "' unavailable. Try a different one.");
        } else {
            return new ModelResponse<>(INTERNAL_SERVER_ERROR, null, "Error creating user. Try again.");
        }
    }

    private User(final String userName, final String password, final String apiKey) {
        this.apiKey = apiKey;
        this.userName = userName;
        this.password = password;
    }

    public ModelResponse<User> delete() {
        final String query = String.format("DELETE FROM USERS WHERE APIKEY='%s'", apiKey);

        final SqlResponse res = DatabaseConnector.runQuery(query);

        if (res.getStatus() == SqlStatus.OK) {
            return new ModelResponse<>(OK, null, "User deleted");
        } else {
            return new ModelResponse<>(INTERNAL_SERVER_ERROR, null, "Error deleting user. Try again.");
        }
    }

    public ModelResponse<User> setPassword(final String password) {
        final String query = String.format("UPDATE USERS SET PASSWORD='%s' WHERE APIKEY='%s'", password, apiKey);

        final SqlResponse res = DatabaseConnector.runQuery(query);

        if (res.getStatus() == SqlStatus.OK) {
            this.password = password;
            return new ModelResponse<>(OK, this, "Successfully changed.");
        } else {
            return new ModelResponse<>(INTERNAL_SERVER_ERROR, null, "Error changing password. Try again.");
        }
    }

    public ModelResponse<User> setUserName(final String userName) {
        final String query = String.format("UPDATE USERS SET USERNAME='%s' WHERE APIKEY='%s'", userName, apiKey);

        final SqlResponse res = DatabaseConnector.runQuery(query);

        if (res.getStatus() == SqlStatus.OK) {
            this.userName = userName;
            return new ModelResponse<>(OK, this, "Successfully changed.");
        } else if (res.getStatus() == SqlStatus.PRIMARY_KEY_VIOLATION) {
            return new ModelResponse<>(CONFLICT, this, "Username '" + userName + "' unavailable. Try a different one.");
        } else {
            return new ModelResponse<>(INTERNAL_SERVER_ERROR, null, "Error changing username. Try again.");
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getPassword() {
        return password;
    }
}
