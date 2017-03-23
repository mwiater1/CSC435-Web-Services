package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;
import com.mateuszwiater.csc435.db.SqlResponse;
import com.mateuszwiater.csc435.db.SqlStatus;

import java.util.ArrayList;
import java.util.List;

import static com.mateuszwiater.csc435.util.HttpStatus.INTERNAL_SERVER_ERROR;
import static com.mateuszwiater.csc435.util.HttpStatus.OK;

public class Category {
    private final String name;

    private Category(final String name) {
        this.name = name;
    }

    public static ModelResponse<List<Category>> getCategories() {
        final String query = "SELECT DISTINCT CATEGORY FROM SOURCES;";

        final SqlResponse res = DatabaseConnector.runQuery(query);

        if (res.getStatus() == SqlStatus.OK) {
            final List<Category> categories = new ArrayList<>();
            res.getData().ifPresent(l -> l.stream().map(l2 -> l2.get(0)).forEach(s -> categories.add(new Category(s))));
            return new ModelResponse<>(OK, categories, "");
        } else {
            return new ModelResponse<>(INTERNAL_SERVER_ERROR, null, "Error retrieving supported categories. Try again.");
        }
    }

    public String getName() {
        return name;
    }
}
