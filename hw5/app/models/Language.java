package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Entity
public class Language extends Model {
    private static Model.Finder<String, Language> find = new Model.Finder<>(Language.class);

    @Id
    private String code;
    @Constraints.Required
    private String name;

    public Language(final String code) {
        this.code = code;
        this.name = new Locale(code).getDisplayLanguage();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Optional<Language> getLanguage(final String code) {
        return Optional.ofNullable(code).map(c -> find.byId(c));
    }

    public static List<Language> getLanguages() {
        return find.all();
    }
}
