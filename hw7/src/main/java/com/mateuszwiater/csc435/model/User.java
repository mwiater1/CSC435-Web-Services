package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class User {
    private transient String password;
    private String userName, apiKey;

    public User(final String userName, final String password) {
        this(userName, password, null);
    }

    private User(final String userName, final String password, final String apiKey) {
        this.apiKey = apiKey;
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void save() throws SQLException {
        String query;
        if (apiKey == null) {
            final String uuid = UUID.randomUUID().toString();
            query = String.format("INSERT INTO USERS (USERNAME,PASSWORD,APIKEY) VALUES ('%s','%s','%s')",
                    userName, password, uuid);
            DatabaseConnector.runQuery(query);
            this.apiKey = uuid;
        } else {
            query = String.format("UPDATE USERS SET USERNAME='%s', PASSWORD = '%s' WHERE APIKEY = '%s'", userName, password, apiKey);
            DatabaseConnector.runQuery(query);
        }
    }

    public void delete() throws SQLException {
        if (apiKey != null) {
            final String query = String.format("DELETE FROM USERS WHERE APIKEY = '%s'", apiKey);
            DatabaseConnector.runQuery(query);
        }
    }

    public static Optional<User> getUser(final UUID apiKey) throws SQLException {
        final String query = String.format("SELECT * " +
                "FROM USERS " +
                "WHERE APIKEY = '%s';", apiKey);

        return getUser(query);
    }

    public static Optional<User> getUser(final String userName, final String password) throws SQLException {
        final String query = String.format("SELECT * " +
                "FROM USERS " +
                "WHERE USERNAME = '%s' " +
                "AND PASSWORD = '%s';", userName, password);

        return getUser(query);
    }

    private static Optional<User> getUser(final String sql) throws SQLException {
        final Optional<List<List<String>>> res = DatabaseConnector.runQuery(sql);

        if (res.isPresent()) {
            final List<String> row = res.get().get(0);
            return Optional.of(new User(row.get(0), row.get(1), row.get(2)));
        } else {
            return Optional.empty();
        }
    }
}
