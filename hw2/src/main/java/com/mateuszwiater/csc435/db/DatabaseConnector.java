package com.mateuszwiater.csc435.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;
import java.util.*;

public class DatabaseConnector {
    private static Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
    private static DatabaseConnector instance;
    private Connection connection;

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

                // Create the Articles table
                st.addBatch("CREATE TABLE ARTICLES (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "AUTHOR VARCHAR(255) NOT NULL," +
                        "TITLE VARCHAR(255) NOT NULL," +
                        "DESCRIPTION VARCHAR(255) NOT NULL," +
                        "URL VARCHAR(255) NOT NULL," +
                        "URLTOIMAGE VARCHAR(255) NOT NULL," +
                        "SOURCE VARCHAR(255) NOT NULL," +
                        "SORTBY VARCHAR(255) NOT NULL," +
                        "PUBLISHEDAT VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (ID));");

                st.addBatch("CREATE TABLE PREFERENCES (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "APIKEY UUID NOT NULL," +
                        "ARTICLEID VARCHAR(255) NOT NULL," +
                        "FAVORITE VARCHAR(255) NOT NULL," +
                        "READ VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (ID));");

                st.executeBatch();

                // Create sources
                Map<String,String> logos = new HashMap<>();
                logos.put("small","http://i.newsapi.org/abc-news-au-s.png");
                logos.put("medium","http://i.newsapi.org/abc-news-au-m.png");
                logos.put("large","http://i.newsapi.org/abc-news-au-l.png");
                List<String> sortBysAvailable = new ArrayList<>();
                sortBysAvailable.add("top");
                Source src = new Source("abc-news-au","ABC News (AU)","Australia's most trusted source of local, national and world news. Comprehensive, independent, in-depth analysis, the latest business, sport, weather and more.",
                        "http://www.abc.net.au/news", "general","en","au",logos, sortBysAvailable);
                src.save();

                logos = new HashMap<>();
                logos.put("small","http://i.newsapi.org/al-jazeera-english-s.png");
                logos.put("medium","http://i.newsapi.org/al-jazeera-english-m.png");
                logos.put("large","http://i.newsapi.org/al-jazeera-english-l.png");
                sortBysAvailable = new ArrayList<>();
                sortBysAvailable.add("top");
                sortBysAvailable.add("latest");
                src = new Source("al-jazeera-english","Al Jazeera English","News, analysis from the Middle East and worldwide, multimedia and interactives, opinions, documentaries, podcasts, long reads and broadcast schedule.",
                        "http://www.aljazeera.com", "general","en","us",logos, sortBysAvailable);
                src.save();

                // Create articles
                Article a = new Article("http://www.abc.net.au/news/david-lipson/7849048",
                        "'My hand on their electoral throat': Glenn Druery lifts lid on One Nation vendetta",
                        "Self-styled preference whisperer Glenn Druery lifts the lid on a personal vendetta against One Nation that he claims goes back almost two decades.",
                        "http://www.abc.net.au/news/2017-04-04/preference-whisperer-glenn-druery-one-nation-micro-party-deals/8415276",
                        "http://www.abc.net.au/news/image/7566444-1x1-700x700.jpg",
                        "abc-news-au",
                        "top",
                        "2017-04-04T13:33:10Z");
                a.save();

                a = new Article("http://www.abc.net.au/news/5511636",
                        "High Court to rule if Bob Day was validly elected",
                        "The High Court will today rule on whether former South Australian senator Bob Day was validly elected to Parliament last year, and how his Upper House crossbench seat should be filled.",
                        "http://www.abc.net.au/news/2017-04-05/high-court-to-rule-if-bob-day-was-validly-elected/8413566",
                        "http://www.abc.net.au/news/image/7986820-1x1-700x700.jpg",
                        "abc-news-au",
                        "top",
                        "2017-04-04T14:14:49Z");
                a.save();

                a = new Article("Hamza Mohamed",
                        "The Gambia: Reviving the tourism industry",
                        "Tourists stopped coming during the country's electoral crisis, but the new government is hopeful good times are ahead.",
                        "http://www.aljazeera.com/indepth/features/2017/03/gambia-reviving-tourism-industry-170313122806481.html",
                        "http://www.aljazeera.com/mritems/Images/2017/4/5/e0475dec96c14c479eca2d4ebe0ebc5b_18.jpg",
                        "al-jazeera-english",
                        "top",
                        "2017-04-06T07:44:00Z");
                a.save();

                a = new Article("Lucia Benavides",
                        "Murdered for being women: Spain tackles femicide rates",
                        "Women are protesting as rates of violence against them rise, forcing the government to act.",
                        "http://www.aljazeera.com/indepth/features/2017/03/murdered-women-spain-tackles-femicide-rates-170319132509999.html",
                        "http://www.aljazeera.com/mritems/Images/2017/3/30/2d765e61e85b4f0fa38f0015cc5e8730_18.jpg",
                        "al-jazeera-english",
                        "top",
                        "2017-04-05T06:09:00Z");
                a.save();

                a = new Article("Nicolas Haque",
                        "Change is coming fast in The Gambia - and it's intoxicating",
                        "As many young Gambians get to participate in their first free and fair election, an unprecedented sense of ownership over the country's future is fast emerging.",
                        "http://www.aljazeera.com/blogs/africa/2017/04/change-coming-fast-gambia-intoxicating-170405235914726.html",
                        "http://www.aljazeera.com/mritems/Images/2017/4/5/5246eba96d0e4150939093be09a0cc89_18.jpg",
                        "al-jazeera-english",
                        "latest",
                        "2017-04-06T07:55:00Z");
                a.save();

                a = new Article("Joseph Stepansky",
                        "What is national concealed carry reciprocity?",
                        "If passed, the law would force every town to honour gun laws of the most permissive states for people from those states.",
                        "http://www.aljazeera.com/indepth/features/2017/03/national-concealed-carry-reciprocity-170330103123777.html",
                        "http://www.aljazeera.com/mritems/Images/2017/4/3/571f2404d9be4e8aacad9e606aa66db4_18.jpg",
                        "al-jazeera-english",
                        "latest",
                        "2017-04-06T06:46:42Z");
                a.save();

                System.out.println("Done!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private DatabaseConnector() {
        try {
            final Gson g = new Gson();
            final Reader config = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("config.json"));
            final DatabaseConfig db = g.fromJson(config, new TypeToken<DatabaseConfig>(){}.getType());
            Class.forName(db.getDriver());
            connection = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());
        } catch (ClassNotFoundException e) {
            logger.error("H2 Driver Class Not Found!", e);
            System.exit(-1);
        } catch (SQLException e) {
            logger.error("Error Establishing Connection To Database!", e);
        }
    }

    private static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
            bootstrap(instance.getConnection());
        }

        return instance;
    }

    private Connection getConnection() {
        return connection;
    }

    public static Optional<List<List<String>>> runQuery(final String query) throws SQLException {
        List<List<String>> table = new ArrayList<>();

        try (final Statement stmt = getInstance().getConnection().createStatement()) {
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

                    return table.size() == 0 ? Optional.empty() : Optional.of(table);
                }
            } else {
                return Optional.empty();
            }
        }
    }

    private static class DatabaseConfig {
        private String url, user, driver, password;

        public DatabaseConfig(final String driver, final String url, final String user, final String password) {
            this.url = url;
            this.user = user;
            this.driver = driver;
            this.password = password;
        }

        String getUrl() {
            return url;
        }

        String getUser() {
            return user;
        }

        String getDriver() {
            return driver;
        }

        String getPassword() {
            return password;
        }
    }
}
