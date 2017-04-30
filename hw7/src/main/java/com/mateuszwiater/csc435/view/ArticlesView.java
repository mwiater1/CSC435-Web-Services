package com.mateuszwiater.csc435.view;

import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.util.Response;

import java.util.List;

import static com.mateuszwiater.csc435.util.Response.*;

public class ArticlesView {

    public static String getView(final Status status, final String message, final List<Article> articles) {
        return new ArticlesResponse(status, message, articles).toJson();
    }

    private static class ArticlesResponse extends Response {
        final List<Article> articles;

        ArticlesResponse(final Status status, final String message, final List<Article> articles) {
            super(status, message);
            this.articles = articles;
        }
    }
}
