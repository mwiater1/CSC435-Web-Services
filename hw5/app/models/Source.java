package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.DbJson;
import com.google.gson.annotations.Expose;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="sources")
public class Source extends Model {
    public static Finder<String, Source> find = new Finder<>(Source.class);

    @Id
    @Expose
    private String id;

    private List<String> sortBysAvailable;

    @DbJson
    @Expose
    private Map<String,String> sortBys;

    @Constraints.Required
    @Size(max = 1000)
    @Expose
    private String url, name, category, language, country, description;

    public Source(String id, String name, String description, String url, String category, String language,
                   String country, List<String> sortBysAvailable) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.country = country;
        this.language = language;
        this.category = category;
        this.description = description;
        this.sortBys = sortBysAvailable.stream().collect(Collectors.toMap(s -> s, s -> s));
    }

    @Override
    public void save() {
        if(sortBysAvailable != null) {
            setSortBysAvailable(sortBysAvailable);
        }
        super.save();
    }

    public static Optional<Source> getSource(final String id) {
        return Optional.ofNullable(find.byId(id));
    }

    public static List<Source> getSources(final Category category, final Language language, final Country country) {
        final ExpressionList<Source> query = find.where();

        Optional.ofNullable(category).ifPresent(c -> query.eq("category", c.getName()));
        Optional.ofNullable(language).ifPresent(l -> query.eq("language", l.getCode()));
        Optional.ofNullable(country).ifPresent(c -> query.eq("country", c.getCode()));

        return query.findList();
    }

    public String getId() {
        return id;
    }

    public List<String> getSortBysAvailable() {
        return new ArrayList<>(sortBys.keySet());
    }

    public void setSortBysAvailable(List<String> sortBys) {
        this.sortBys = sortBys.stream().collect(Collectors.toMap(s -> s, s -> s));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}