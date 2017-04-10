package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class SourceTest {
    @Before
    public void before() throws SQLException {
        // Create the sources table
        DatabaseConnector.runQuery("CREATE TABLE SOURCES (" +
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

        // Create the test sources
        final Map<String, String> urlsToLogos = new HashMap<>();
        urlsToLogos.put("small","http://example.com/small");
        urlsToLogos.put("medium","http://example.com/medium");
        urlsToLogos.put("large","http://example.com/large");
        final List<String> sortBysAvailable = new ArrayList<>();
        sortBysAvailable.add("top");
        sortBysAvailable.add("latest");
        new Source("test","Test News", "Description", "http://example.com/test", "general", "en", "us", urlsToLogos, sortBysAvailable).save();
        new Source("test2","Test News2", "Description", "http://example.com/test2", "music", "fr", "au", urlsToLogos, sortBysAvailable).save();
        new Source("test3","Test News3", "Description", "http://example.com/test3", "science", "de", "it", urlsToLogos, sortBysAvailable).save();
    }

    @After
    public void after() throws SQLException {
        // Drop the sources table
        DatabaseConnector.runQuery("DROP TABLE SOURCES;");
    }

    @Test
    public void getSingleInvalidSource() throws SQLException {
        final Optional<Source> source = Source.getSource("source");

        // Make sure a source is not returned
        assertThat(source.isPresent(), is(equalTo(false)));
    }

    @Test
    public void getSingleValidSource() throws SQLException {
        final Optional<Source> source = Source.getSource("test");

        // Make sure a source is returned
        assertThat(source.isPresent(), is(equalTo(true)));

        // Make sure that the source properties are correct.
        final Map<String, String> urlsToLogos = new HashMap<>();
        urlsToLogos.put("small","http://example.com/small");
        urlsToLogos.put("medium","http://example.com/medium");
        urlsToLogos.put("large","http://example.com/large");

        final List<String> sortBysAvailable = new ArrayList<>();
        sortBysAvailable.add("top");
        sortBysAvailable.add("latest");

        assertThat(source.get().getId(), is(equalTo("test")));
        assertThat(source.get().getName(), is(equalTo("Test News")));
        assertThat(source.get().getDescription(), is(equalTo("Description")));
        assertThat(source.get().getUrl(), is(equalTo("http://example.com/test")));
        assertThat(source.get().getCategory(), is(equalTo("general")));
        assertThat(source.get().getLanguage(), is(equalTo("en")));
        assertThat(source.get().getCountry(), is(equalTo("us")));
        assertThat(source.get().getUrlsToLogos(), is(equalTo(urlsToLogos)));
        assertThat(source.get().getSortBysAvailable(), is(equalTo(sortBysAvailable)));
    }

    @Test
    public void filterByCategory() throws SQLException {
        // Make sure the category exists
        Optional<Category> category = Category.getCategory("music");
        assertThat(category.isPresent(), is(equalTo(true)));

        // Get the sources matching the category
        final List<Source> source = Source.getSources(category.get(), null, null);

        // Make sure the correct sources are returned
        assertThat(source.size(), is(equalTo(1)));

        // Make sure that the source properties are correct.
        final Map<String, String> urlsToLogos = new HashMap<>();
        urlsToLogos.put("small","http://example.com/small");
        urlsToLogos.put("medium","http://example.com/medium");
        urlsToLogos.put("large","http://example.com/large");

        final List<String> sortBysAvailable = new ArrayList<>();
        sortBysAvailable.add("top");
        sortBysAvailable.add("latest");

        assertThat(source.get(0).getId(), is(equalTo("test2")));
        assertThat(source.get(0).getName(), is(equalTo("Test News2")));
        assertThat(source.get(0).getDescription(), is(equalTo("Description")));
        assertThat(source.get(0).getUrl(), is(equalTo("http://example.com/test2")));
        assertThat(source.get(0).getCategory(), is(equalTo("music")));
        assertThat(source.get(0).getLanguage(), is(equalTo("fr")));
        assertThat(source.get(0).getCountry(), is(equalTo("au")));
        assertThat(source.get(0).getUrlsToLogos(), is(equalTo(urlsToLogos)));
        assertThat(source.get(0).getSortBysAvailable(), is(equalTo(sortBysAvailable)));
    }

    @Test
    public void filterByLanguage() throws SQLException {
        // Make sure the language exists
        Optional<Language> language = Language.getLanguage("de");
        assertThat(language.isPresent(), is(equalTo(true)));

        // Get the sources matching the language
        final List<Source> source = Source.getSources(null, language.get(), null);

        // Make sure the correct sources are returned
        assertThat(source.size(), is(equalTo(1)));

        // Make sure that the source properties are correct.
        final Map<String, String> urlsToLogos = new HashMap<>();
        urlsToLogos.put("small","http://example.com/small");
        urlsToLogos.put("medium","http://example.com/medium");
        urlsToLogos.put("large","http://example.com/large");

        final List<String> sortBysAvailable = new ArrayList<>();
        sortBysAvailable.add("top");
        sortBysAvailable.add("latest");

        assertThat(source.get(0).getId(), is(equalTo("test3")));
        assertThat(source.get(0).getName(), is(equalTo("Test News3")));
        assertThat(source.get(0).getDescription(), is(equalTo("Description")));
        assertThat(source.get(0).getUrl(), is(equalTo("http://example.com/test3")));
        assertThat(source.get(0).getCategory(), is(equalTo("science")));
        assertThat(source.get(0).getLanguage(), is(equalTo("de")));
        assertThat(source.get(0).getCountry(), is(equalTo("it")));
        assertThat(source.get(0).getUrlsToLogos(), is(equalTo(urlsToLogos)));
        assertThat(source.get(0).getSortBysAvailable(), is(equalTo(sortBysAvailable)));
    }

    @Test
    public void filterByCountry() throws SQLException {
        // Make sure the category exists
        Optional<Country> country = Country.getCountry("us");
        assertThat(country.isPresent(), is(equalTo(true)));

        // Get the sources matching the category
        final List<Source> source = Source.getSources(null, null, country.get());

        // Make sure the correct sources are returned
        assertThat(source.size(), is(equalTo(1)));

        // Make sure that the source properties are correct.
        final Map<String, String> urlsToLogos = new HashMap<>();
        urlsToLogos.put("small","http://example.com/small");
        urlsToLogos.put("medium","http://example.com/medium");
        urlsToLogos.put("large","http://example.com/large");

        final List<String> sortBysAvailable = new ArrayList<>();
        sortBysAvailable.add("top");
        sortBysAvailable.add("latest");

        assertThat(source.get(0).getId(), is(equalTo("test")));
        assertThat(source.get(0).getName(), is(equalTo("Test News")));
        assertThat(source.get(0).getDescription(), is(equalTo("Description")));
        assertThat(source.get(0).getUrl(), is(equalTo("http://example.com/test")));
        assertThat(source.get(0).getCategory(), is(equalTo("general")));
        assertThat(source.get(0).getLanguage(), is(equalTo("en")));
        assertThat(source.get(0).getCountry(), is(equalTo("us")));
        assertThat(source.get(0).getUrlsToLogos(), is(equalTo(urlsToLogos)));
        assertThat(source.get(0).getSortBysAvailable(), is(equalTo(sortBysAvailable)));
    }
}
