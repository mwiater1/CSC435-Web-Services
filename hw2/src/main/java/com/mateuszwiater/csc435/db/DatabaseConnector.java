package com.mateuszwiater.csc435.db;

import com.mateuszwiater.csc435.model.ModelResponse;
import com.mateuszwiater.csc435.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DatabaseConnector {
    private static Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
    private static DatabaseConnector instance;
    private Connection connection;

    public static void main(String[] args) {
        bootstrap(getConnection());

        Locale l = new Locale("", "au");
        System.out.println("Country: " + l.getDisplayCountry());
    }

    private static void bootstrap(final Connection con) {
        try {
            ResultSet rs = con.getMetaData().getTables(null, null, null, new String[]{"TABLE"});

            // Check if DB is empty.
            rs.last();
            if (rs.getRow() == 0) {
                // Create the Users table.
                System.out.print("Bootstrapping...");
                Statement st = con.createStatement();
                st.addBatch("CREATE TABLE USERS (" +
//                        "ID int NOT NULL AUTO_INCREMENT," +
                        "USERNAME VARCHAR(255) NOT NULL," +
                        "PASSWORD VARCHAR(255) NOT NULL," +
                        "APIKEY UUID NOT NULL," +
                        "PRIMARY KEY (USERNAME));");

                // Create the Sources table
                st.addBatch("CREATE TABLE SOURCES (" +
                        "ID VARCHAR(255) NOT NULL," +
                        "NAME VARCHAR(255) NOT NULL," +
                        "DESCRIPTION VARCHAR(255) NOT NULL," +
                        "URL VARCHAR(255) NOT NULL," +
                        "CATEGORY VARCHAR(255) NOT NULL," +
                        "LANGUAGE VARCHAR(255) NOT NULL," +
                        "COUNTRY VARCHAR(255) NOT NULL," +
                        "LOGOS VARCHAR(255) NOT NULL," +
                        "SORTBYS VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (ID));");

                // Add dummy users
                st.addBatch("INSERT INTO " +
                        "USERS (USERNAME,PASSWORD,APIKEY)" +
                        "VALUES ('mwiater','abc123','" + UUID.randomUUID() + "');");

                st.addBatch("INSERT INTO " +
                        "USERS (USERNAME,PASSWORD,APIKEY)" +
                        "VALUES ('awiater','123abc','" + UUID.randomUUID() + "');");

                st.executeBatch();

                System.out.println("Done!");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private DatabaseConnector() {
        try {
            Class.forName("org.h2.Driver");
//            connection = DriverManager.getConnection("jdbc:h2:file:~/Newsly", "mwiater", "csc435pass");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Newsly", "mwiater", "csc435pass");
        } catch (ClassNotFoundException e) {
            logger.error("H2 Driver Class Not Found!", e);
            System.exit(-1);
        } catch (SQLException e) {
            logger.error("Error Establishing Connection To Database!", e);
        }
    }

    private static Connection getConnection() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance.connection;
    }

    public static SqlResponse runQuery(final String query) {
        List<List<String>> table = new ArrayList<>();

        try (final Statement stmt = getConnection().createStatement()) {
            // Check if there is a ResultSet
            if (stmt.execute(query)) {
                // Convert the ResultSet to List<List<String>>
                try (final ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        final List<String> row = new ArrayList<>();
                        for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
                            row.add(rs.getString(i));
                        }
                        table.add(row);
                    }

                    return new SqlResponse(SqlStatus.OK, table.size() == 0 ? null : table, "");
                }
            } else {
                return new SqlResponse(SqlStatus.OK, null, "");
            }
        } catch (SQLException e) {
            // Check for Primary Key Violation
            if(e.getSQLState().equals("23505")) {
                return new SqlResponse(SqlStatus.PRIMARY_KEY_VIOLATION, null, "");
            } else {
                logger.error("SQL Error", e);
                return new SqlResponse(SqlStatus.SQL_ERROR, null, "");
            }
        }
    }
}
