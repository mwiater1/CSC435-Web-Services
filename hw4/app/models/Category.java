package models;

public class Category {
    private final String name;

    private Category(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
