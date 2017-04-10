package com.mateuszwiater.csc435.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mateuszwiater.csc435.model.Source;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

class SourcesLoader {
    private static Logger logger = LoggerFactory.getLogger(SourcesLoader.class);

    static void loadSources() throws URISyntaxException, IOException, SQLException {
        URI uri = new URIBuilder().setScheme("https")
                .setHost("newsapi.org")
                .setPath("/v1/sources")
                .build();

        logger.info("Loading Sources From: " + uri.toString());
        logger.info("Querying Server...");
        try (InputStreamReader reader = new InputStreamReader(HttpClients.createDefault().execute(new HttpGet(uri)).getEntity().getContent())) {
            logger.info("Converting Response to Source Objects...");
            Gson g = new Gson();
            final Type sourcesType = new TypeToken<SourcesBootstrap>() {}.getType();
            SourcesBootstrap bootstrap = g.fromJson(reader,sourcesType);

            logger.info("Saving Sources to Database...");
            for (Source source : bootstrap.getSources()) {
                source.save();
            }

            logger.info("Sources Successfully Loaded!");
        }
    }

    private class SourcesBootstrap {
        private String status;
        private List<Source> sources;

        public String getStatus() {
            return status;
        }

        public List<Source> getSources() {
            return sources;
        }
    }
}
