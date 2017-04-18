package controllers;

import actions.Auth;
import models.Article;
import models.Preference;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.PreferenceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@With(Auth.class)
@SuppressWarnings("Duplicates")
public class PreferenceController extends Controller {

    public Result doGet() {
        boolean favoriteParam = Boolean.valueOf(request().getQueryString("favorite"));
        boolean readParam = Boolean.valueOf(request().getQueryString("read"));
        User user = User.getUser(UUID.fromString(session("apiKey"))).get();

        // Build preference map
        Map<Long, Preference> preferenceMap = user.getPreferences().stream().collect(Collectors.toMap(Preference::getArticleId, Function.identity()));

        // Build the articles list
        List<Article> articles = new ArrayList<>();
        for (Preference preference : preferenceMap.values()) {
            // Only get articles which are preferred
            if(preference.isFavorite() || preference.isRead()) {
                if(favoriteParam && preference.isFavorite()) {
                    Article.getArticle(preference.getArticleId()).ifPresent(articles::add);
                    continue;
                }

                if(readParam && preference.isRead()) {
                    Article.getArticle(preference.getArticleId()).ifPresent(articles::add);
                }
            }
        }

        return ok(PreferenceView.render(articles, preferenceMap, favoriteParam, readParam));
    }

    public Result doPost() {
        String favoriteParam = request().body().asFormUrlEncoded().getOrDefault("favorite", new String[]{null})[0];
        String readParam = request().body().asFormUrlEncoded().getOrDefault("read", new String[]{null})[0];
        String articleIdParam = request().body().asFormUrlEncoded().getOrDefault("userName", new String[]{null})[0];
        User user = User.getUser(UUID.fromString(session("apiKey"))).get();

        if(articleIdParam == null || articleIdParam.isEmpty()) {
            return status(400);
        } else if(favoriteParam != null || readParam != null) {
            final Preference preference = user.getPreference(Long.valueOf(articleIdParam));

            if(favoriteParam != null) {
                preference.setFavorite(Boolean.valueOf(favoriteParam));
            }

            if(readParam != null) {
                preference.setRead(Boolean.valueOf(readParam));
            }

            preference.save();
        }

        return ok();
    }
}
