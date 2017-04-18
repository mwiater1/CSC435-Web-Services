package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

import static services.Util.orElse;

@Entity
public class Article extends Model {
    private static Finder<Long, Article> find = new Finder<>(Article.class);

    @Id
    private long id;

    private String url, title, sortBy, author, sourceId, urlToImage, publishedAt, description;

    public Article(final String author, final String title, final String description, final String url,
                   final String urlToImage, final String sourceId, final String sortBy, final String publishedAt) {
        this.url = url;
        this.title = title;
        this.sortBy = sortBy;
        this.author = author;
        this.sourceId = sourceId;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.description = description;
    }

    public static Optional<Article> getArticle(final long id) {
        return Optional.ofNullable(find.byId(id));
    }

    public static List<Article> getArticles(final Source source, final String sortBy) {
        return find.where()
                .eq("sourceId", source.getId())
                .eq("sortBy", sortBy)
                .findList();
    }

    public long getId() {
        return id;
    }

    public String getSourceId() {
        return orElse(sourceId);
    }

    public void setSourceId(final String sourceId) {
        this.sourceId = sourceId;
    }

    public String getUrl() {
        return orElse(url, "#");
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getTitle() {
        return orElse(title);
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getSortBy() {
        return orElse(sortBy);
    }

    public void setSortBy(final String sortBy) {
        this.sortBy = sortBy;
    }

    public String getAuthor() {
        return orElse(author);
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getUrlToImage() {
        return orElse(urlToImage);
    }

    public void setUrlToImage(final String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return orElse(publishedAt);
    }

    public void setPublishedAt(final String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDescription() {
        return orElse(description);
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
