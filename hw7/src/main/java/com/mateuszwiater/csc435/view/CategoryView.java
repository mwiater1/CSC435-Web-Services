package com.mateuszwiater.csc435.view;

import com.mateuszwiater.csc435.model.Category;
import com.mateuszwiater.csc435.util.Response;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryView {

    public static String getView(final Response.Status status, final String message, final List<Category> category) {
        return new CategoryResponse(status, message, category.stream().map(Category::getName).collect(Collectors.toList())).toJson();
    }

    private static class CategoryResponse extends Response {
        final List<String> categories;

        CategoryResponse(final Status status, final String message, final List<String> countries) {
            super(status, message);
            this.categories = countries;
        }
    }
}
