package controllers;

import actions.RestfulAuth;
import models.Article;
import models.Preference;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.Status;
import views.html.JsonPreferenceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@With(RestfulAuth.class)
public class RestfulPreferenceController extends Controller {

    public Result doGet() {
        final boolean read = Boolean.valueOf(request().getQueryString("read"));
        final boolean favorite = Boolean.valueOf(request().getQueryString("favorite"));
        final String apiKey = request().getQueryString("apiKey");

        final List<Preference> preferences = Preference.getPreferences(UUID.fromString(apiKey)).stream()
                .filter(p -> (p.isFavorite() && favorite) || (p.isRead() && read))
                .collect(Collectors.toList());

        final List<Article> articles = new ArrayList<>();

        for (Preference preference : preferences) {
            Article.getArticle(preference.getArticleId()).ifPresent(articles::add);
        }

        return ok(JsonPreferenceView.render(Status.OK, "", articles)).as("application/json; charset=UTF-8");
    }

    public Result doPost() {
        final String readParam = request().getQueryString("read");
        final String favoriteParam = request().getQueryString("favorite");
        final String articleIdParam = request().getQueryString("articleId");
        final String apiKey = request().getQueryString("apiKey");

        Optional<Article> article;

        try {
            article = Article.getArticle(Long.valueOf(articleIdParam));
        } catch (NumberFormatException e) {
            article = Optional.empty();
        }

        if(article.isPresent()) {
            if(favoriteParam != null || readParam != null) {
                final Preference preference = Preference.getPreference(UUID.fromString(apiKey), Long.valueOf(articleIdParam)).orElse(new Preference(UUID.fromString(apiKey), Long.valueOf(articleIdParam), false, false));

                if (favoriteParam != null) {
                    preference.setFavorite(Boolean.valueOf(favoriteParam));
                }

                if (readParam != null) {
                    preference.setRead(Boolean.valueOf(readParam));
                }

                preference.save();
            }
            return ok(JsonPreferenceView.render(Status.OK, "", new ArrayList<>())).as("application/json; charset=UTF-8");
        } else {
            return status(400, JsonPreferenceView.render(Status.FAIL, "Invalid Article ID: '" + articleIdParam + "'", new ArrayList<>())).as("application/json; charset=UTF-8");
        }
    }
}
