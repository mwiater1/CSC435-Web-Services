package models;

public class Preference {
    private final String apiKey, articleId;
    private boolean favorite, read;

    public Preference(final String apiKey, final String articleId, final boolean favorite, final boolean read) {
        this.read = read;
        this.apiKey = apiKey;
        this.favorite = favorite;
        this.articleId = articleId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getArticleId() {
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
