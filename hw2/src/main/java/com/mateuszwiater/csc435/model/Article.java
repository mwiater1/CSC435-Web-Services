package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Article {
    private String id;
    private final String author, title, description, url, urlToImage, source, publishedAt;

    public Article(String author, String title, String description, String url, String urlToImage, String source, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.source = source;
        this.publishedAt = publishedAt;
    }

    public void save() throws SQLException {
        if(id == null) {
            final String query = String.format("INSERT INTO ARTICLES (AUTHOR,TITLE,DESCRIPTION,URL,URLTOIMAGE,SOURCE,PUBLISHEDAT) VALUES ('%s','%s','%s','%s','%s','%s','%s');",
                    author, title, description, url, urlToImage, source, publishedAt);
            DatabaseConnector.runQuery(query);
        }
    }

    public static Optional<Article> getArticle(final String id) throws SQLException {
        final String query = String.format("SELECT ID, AUTHOR, TITLE, DESCRIPTION, URL, URLTOIMAGE, SOURCE, PUBLISHEDAT FROM ARTICLES WHERE ID = '%s';",
                id);

        Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        if(res.isPresent()) {
            return Optional.of(toArticle(res.get().get(0)));
        } else {
            return Optional.empty();
        }
    }

    public static List<Article> getArticles(final Source source, final String sortBy) throws SQLException {
        final String query = String.format("SELECT ID, AUTHOR, TITLE, DESCRIPTION, URL, URLTOIMAGE, SOURCE, PUBLISHEDAT FROM ARTICLES WHERE SOURCE = '%s' AND SORTBY = '%s';",
                source, sortBy);

        Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);
        final List<Article> articles = new ArrayList<>();

        if(res.isPresent()) {
            res.get().forEach(l -> articles.add(toArticle(l)));
        }

        return articles;
    }

    private static Article toArticle(final List<String> list) {
        final Article article = new Article(list.get(1),list.get(2),list.get(3),list.get(4),list.get(5),list.get(6),list.get(7));
        article.id = list.get(0);
        return article;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getSource() {
        return source;
    }

    public String getPublishedAt() {
        return publishedAt;
    }


}
