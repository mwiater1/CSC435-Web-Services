package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
public class Source extends Model {
    private static Finder<String, Source> find = new Finder<>(Source.class);

    @Id
    private String id;
    @Constraints.Required
    private URL url;
    @Constraints.Required
    private List<String> sortBysAvailable;
    @Constraints.Required
    private Map<String, URL> urlsToLogos;
    @Constraints.Required
    private String name, description;
    @Constraints.Required
    @OneToMany
    private Category category;
    @Constraints.Required
    private Language language;
    @Constraints.Required
    private Country country;

    public Source(final String id, final String name, final String description, final URL url, final Category category,
                  final Language language, final Country country, final Map<String, URL> urlsToLogos, final List<String> sortBysAvailable) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.country = country;
        this.language = language;
        this.category = category;
        this.description = description;
        this.urlsToLogos = urlsToLogos;
        this.sortBysAvailable = sortBysAvailable;
    }

    public String getId() {
        return id;
    }

    public URL getUrl() {
        return url;
    }

    public List<String> getSortBysAvailable() {
        return sortBysAvailable;
    }

    public Map<String, URL> getUrlsToLogos() {
        return urlsToLogos;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Language getLanguage() {
        return language;
    }

    public Country getCountry() {
        return country;
    }

    public static Optional<Source> getSource(final String id) {
        return Optional.ofNullable(id).map(i -> find.byId(i));
    }

    public static List<Source> getSources(final Category category, final Language language, final Country country) {
        final ExpressionList<Source> query = find.where();

        Optional.ofNullable(country).ifPresent(c -> query.eq("country", c));
        Optional.ofNullable(category).ifPresent(c -> query.eq("category", c));
        Optional.ofNullable(language).ifPresent(l -> query.eq("language", l));

        return query.findList();
    }
}