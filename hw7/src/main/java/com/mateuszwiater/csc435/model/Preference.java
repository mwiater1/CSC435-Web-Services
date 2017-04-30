package com.mateuszwiater.csc435.model;

import com.mateuszwiater.csc435.db.DatabaseConnector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Preference {
    private final String apiKey, articleId;
    private boolean favorite, read, isNew;

    public Preference(final String apiKey, final String articleId, final boolean favorite, final boolean read) {
        this.read = read;
        this.isNew = true;
        this.apiKey = apiKey;
        this.favorite = favorite;
        this.articleId = articleId;
    }

    public void save() throws SQLException {
        if(isNew) {
            final String query = String.format("INSERT INTO PREFERENCES (APIKEY,ARTICLEID,FAVORITE,READ) VALUES ('%s','%s','%s','%s');",
                    apiKey, articleId, favorite, read);
            DatabaseConnector.runQuery(query);
        } else {
            final String query = String.format("UPDATE PREFERENCES SET FAVORITE = '%s', READ = '%s' WHERE APIKEY = '%s' AND ARTICLEID = '%s';",
                    favorite, read, apiKey, articleId);
            DatabaseConnector.runQuery(query);
        }
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

    public static Optional<Preference> getPreference(final String apiKey, final String articleId) throws SQLException {
        final String query = String.format("SELECT APIKEY, ARTICLEID, FAVORITE, READ FROM PREFERENCES WHERE APIKEY = '%s' AND ARTICLEID = '%s';", apiKey, articleId);

        final Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        if(res.isPresent()) {
            return Optional.of(toPreference(res.get().get(0)));
        } else {
            return Optional.empty();
        }
    }

    public static List<Preference> getPreferences(final String apiKey) throws SQLException {
        final String query = String.format("SELECT APIKEY, ARTICLEID, FAVORITE, READ FROM PREFERENCES WHERE APIKEY = '%s';", apiKey);

        final Optional<List<List<String>>> res = DatabaseConnector.runQuery(query);

        if(res.isPresent()) {
            return res.get().stream().map(Preference::toPreference).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    private static Preference toPreference(final List<String> list) {
        final Preference preference = new Preference(list.get(0), list.get(1), Boolean.valueOf(list.get(2)), Boolean.valueOf(list.get(3)));
        preference.isNew = false;
        return preference;
    }
}
