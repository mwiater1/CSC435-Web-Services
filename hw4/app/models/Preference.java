package models;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Preference> getPreferences() {
        final List<Preference> preferences = new ArrayList<>();
        preferences.add(new Preference("apiKey", "1", true, true));
        preferences.add(new Preference("apiKey", "2", false, true));
        preferences.add(new Preference("apiKey", "4", true, false));

        return preferences;
    }
}
