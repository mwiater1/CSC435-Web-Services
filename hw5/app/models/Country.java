package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Entity
public class Country extends Model {
    private static Model.Finder<String, Country> find = new Model.Finder<>(Country.class);

    @Id
    private String code;
    @Constraints.Required
    private String name;

    public Country(final String code) {
        this.code = code;
        this.name = new Locale("", code).getDisplayCountry();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static Optional<Country> getCountry(final String code) {
        return Optional.ofNullable(code).map(c -> find.byId(c));
    }

    public static List<Country> getCountries() {
        return find.all();
    }
}
