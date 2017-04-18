package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Entity
public class Category extends Model {
    private static Model.Finder<String, Category> find = new Model.Finder<>(Category.class);

    @Id
    private String name;

    public Category(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<Category> getCategory(final String name) {
        return Optional.ofNullable(name).map(n -> find.byId(n));
    }

    public static List<Category> getCategories() {
        return find.all();
    }
}
