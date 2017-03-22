package com.mateuszwiater.csc435.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Scanner;
import java.util.UUID;

public class DatabaseConnector {
    private static Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
    private static DatabaseConnector instance;
    private Connection connection;

    public static void main(String[] args) {
        bootstrap(getConnection());
        Scanner s = new Scanner(System.in);
        String in = s.nextLine();
        while(!in.equals("quit")) {


            in = s.nextLine();
        }
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
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "USERNAME VARCHAR(255) NOT NULL," +
                        "PASSWORD VARCHAR(255) NOT NULL," +
                        "APIKEY UUID NOT NULL," +
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

    public static Connection getConnection() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance.connection;
    }
}
