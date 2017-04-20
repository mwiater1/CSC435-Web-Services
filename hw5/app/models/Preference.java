package models;

import com.avaje.ebean.Model;
import com.google.gson.annotations.Expose;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name="preferences")
public class Preference extends Model {
    private static Finder<Long, Preference> find = new Finder<>(Preference.class);

    @Id
    @Expose
    private long id;
    @Constraints.Required
    @Expose
    private UUID apiKey;
    @Constraints.Required
    @Expose
    private long articleId;
    @Constraints.Required
    @Expose
    private boolean favorite, read;

    public Preference(final UUID apiKey, final long articleId, final boolean favorite, final boolean read) {
        this.read = read;
        this.apiKey = apiKey;
        this.favorite = favorite;
        this.articleId = articleId;
    }

    public static Optional<Preference> getPreference(final UUID apiKey, final long articleId) {
        return Optional.ofNullable(find.where()
                .eq("apiKey", apiKey)
                .eq("articleId", articleId)
                .findUnique());
    }

    public static List<Preference> getPreferences(final UUID apiKey) {
        return find.where().eq("apiKey", apiKey).findList();
    }

    public long getId() {
        return id;
    }

    public UUID getApiKey() {
        return apiKey;
    }

    public long getArticleId() {
        return articleId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
