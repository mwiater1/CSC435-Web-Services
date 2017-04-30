package com.mateuszwiater.csc435.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mateuszwiater.csc435.model.Article;
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

public class ArticlesLoader {
    private static Logger logger = LoggerFactory.getLogger(ArticlesLoader.class);

    public static void loadArticles(final String apiKey) throws SQLException, URISyntaxException, IOException {
        List<Source> sources = Source.getSources(null, null, null);

        for (Source source : sources) {
            logger.info("Loading Articles From: " + source.getName());
            for (String s : source.getSortBysAvailable()) {
                URI uri = new URIBuilder().setScheme("https")
                        .setHost("newsapi.org")
                        .setPath("/v1/articles")
                        .setParameter("apiKey",apiKey)
                        .setParameter("source", source.getId())
                        .setParameter("sortBy",s)
                        .build();

                logger.info("\tLoading SortBy: " + s);
                try (InputStreamReader reader = new InputStreamReader(HttpClients.createDefault().execute(new HttpGet(uri)).getEntity().getContent())) {
                    Gson g = new Gson();
                    final Type articlesType = new TypeToken<ArticlesBootstrap>() {}.getType();
                    ArticlesBootstrap bootstrap = g.fromJson(reader,articlesType);

                    for (Article article : bootstrap.getArticles()) {
                        article.setSortBy(s);
                        article.setSourceId(source.getId());
                        article.save();
                    }
                }
            }
        }

        logger.info("Articles Loaded Successfully!");
    }

    private class ArticlesBootstrap {
        private String status, source, sortBy;
        private List<Article> articles;

        public String getStatus() {
            return status;
        }

        public String getSource() {
            return source;
        }

        public String getSortBy() {
            return sortBy;
        }

        public List<Article> getArticles() {
            return articles;
        }
    }
}
