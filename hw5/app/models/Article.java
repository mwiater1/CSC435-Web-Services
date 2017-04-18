package models;

import com.avaje.ebean.Model;
import org.joda.time.DateTime;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Entity
public class Article extends Model {
    private static Finder<Long, Article> find = new Finder<>(Article.class);

    @Id
    private long id;

    @Constraints.Required
    private String sortBy;
    @Constraints.Required
    private Source source;

    private DateTime publishedAt;

    private String author, title, description, url, urlToImage;

    public Article(final String author, final String title, final String description, final String url,
                   final String urlToImage, final Source source, final String sortBy, final DateTime publishedAt) {
        this.url = url;
        this.title = title;
        this.sortBy = sortBy;
        this.author = author;
        this.source = source;
        this.urlToImage = urlToImage;
        this.description = description;
        this.publishedAt = publishedAt;
    }

    public long getId() {
        return id;
    }

    public String getSortBy() {
        return sortBy;
    }

    public Source getSource() {
        return source;
    }

    public DateTime getPublishedAt() {
        return publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public static Optional<Article> getArticle(final long id) {
        return Optional.ofNullable(find.byId(id));
    }

    public static List<Article> getArticles(final Source source, final String sortBy) {
        return find.where().eq("sourceId", source.getId()).eq("sortBy",sortBy).findList();
    }
}
