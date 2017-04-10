package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class HistoryTest {
    @Before
    public void before() throws SQLException {
        // Create the history table
        DatabaseConnector.runQuery("CREATE TABLE HISTORY (" +
                "ID int NOT NULL AUTO_INCREMENT," +
                "APIKEY UUID NOT NULL," +
                "ENDPOINT VARCHAR(255) NOT NULL," +
                "REQUESTTYPE VARCHAR(255) NOT NULL," +
                "REQUESTTIME VARCHAR(255) NOT NULL," +
                "PARAMETERS VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (ID));");

        // Create the test histories
        final LocalDateTime time = LocalDateTime.of(LocalDate.of(2017,1,1), LocalTime.of(1,1));
        final Map<String, String> map = new HashMap<>();
        map.put("a","b");
        map.put("c","d");
        new History("2a998745-125c-4fd6-b4c3-d0fff452aace", "test", "get", time, map).save();
        new History("2a998745-125c-4fd6-b4c3-d0fff452aace", "test2", "post", time, map).save();
    }

    @After
    public void after() throws SQLException {
        // Drop the history table
        DatabaseConnector.runQuery("DROP TABLE HISTORY;");
    }

    @Test
    public void getValidHistory() throws SQLException {
        final LocalDateTime time = LocalDateTime.of(LocalDate.of(2017,1,1), LocalTime.of(1,1));
        final Map<String, String> map = new HashMap<>();
        map.put("a","b");
        map.put("c","d");

        List<History> res = History.getHistory("2a998745-125c-4fd6-b4c3-d0fff452aace");

        // Make sure the list is not empty
        assertThat(res.size(), is(equalTo(2)));

        // Make sure both results have expected information
        assertThat(res.get(0).getEndpoint(), is(equalTo("test")));
        assertThat(res.get(0).getRequestType(), is(equalTo("get")));
        assertThat(res.get(0).getRequestTime(), is(equalTo(time)));
        assertThat(res.get(0).getParameters(), is(equalTo(map)));

        assertThat(res.get(1).getEndpoint(), is(equalTo("test2")));
        assertThat(res.get(1).getRequestType(), is(equalTo("post")));
        assertThat(res.get(1).getRequestTime(), is(equalTo(time)));
        assertThat(res.get(1).getParameters(), is(equalTo(map)));
    }

}
