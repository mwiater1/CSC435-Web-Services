package models;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Category extends CodeName {

    private Category(final String name) {
        super(name, name);
    }

    public static Optional<Category> getCategory(final String category) {
        return Optional.ofNullable(Source.find
                .select("category")
                .setDistinct(true)
                .where()
                .eq("category", category)
                .findUnique()
        ).map(s -> new Category(s.getCategory()));
    }

    public static List<Category> getCategories() {
        return Source.find
                .select("category")
                .setDistinct(true)
                .findSet()
                .stream()
                .map(s -> new Category(s.getCategory()))
                .collect(Collectors.toList());
    }
}
