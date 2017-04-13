package models;

import java.util.Collections;
import java.util.List;

public class Category {
    private final String name;

    private Category(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Category> getCategories() {
        return Collections.singletonList(new Category("general"));
    }
}
