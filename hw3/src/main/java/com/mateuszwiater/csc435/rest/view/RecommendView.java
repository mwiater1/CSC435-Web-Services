package com.mateuszwiater.csc435.rest.view;

import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.rest.util.Response;

public class RecommendView {

    public static String getView(final Response.Status status, final String message, final Article article) {
        return new RecommendResponse(status, message, article).toJson();
    }

    private static class RecommendResponse extends Response {
        final Article article;

        RecommendResponse(final Status status, final String message, final Article article) {
            super(status, message);
            this.article = article;
        }
    }
}
