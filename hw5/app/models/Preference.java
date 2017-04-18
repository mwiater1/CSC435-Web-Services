package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Preference extends Model {
    @Id
    private long id;

    @Constraints.Required
    private final long articleId;

    @Constraints.Required
    private boolean favorite, read;

    Preference(final long articleId) {
        this.articleId = articleId;
    }

    public long getId() {
        return id;
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
