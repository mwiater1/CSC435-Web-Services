package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Category {
    private final String name;

    private Category(final String name) {
        this.name = name;
    }

    public static Optional<Category> getCategory(final String category) throws SQLException {
        final String query = String.format("SELECT DISTINCT CATEGORY FROM SOURCES WHERE CATEGORY = '%s';", category);

        final Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        if (res.isPresent()) {
            return Optional.of(new Category(category));
        } else {
            return Optional.empty();
        }
    }

    public static List<Category> getCategories() throws SQLException {
        final String query = "SELECT DISTINCT CATEGORY FROM SOURCES;";

        final Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        if (res.isPresent()) {
            return res.get().stream().map(l -> l.get(0)).map(Category::new).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public String getName() {
        return name;
    }
}
