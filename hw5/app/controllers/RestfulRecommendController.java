package controllers;

import actions.RestfulAuth;
import models.Article;
import models.Preference;
import models.Source;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.Status;
import views.html.JsonRecommendView;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@With(RestfulAuth.class)
public class RestfulRecommendController extends Controller {

    public Result doGet() {
        final String apiKey = request().getQueryString("apiKey");

        final List<Preference> preferences = Preference.getPreferences(UUID.fromString(apiKey)).stream()
                .filter(Preference::isFavorite)
                .collect(Collectors.toList());
        final Optional<Preference> preference = preferences.stream()
                .skip(ThreadLocalRandom.current().nextInt(preferences.size()))
                .findAny();
        if(preference.isPresent()) {
            final Optional<Article> article = Article.getArticle(preference.get().getArticleId());
            if(article.isPresent()) {
                final Optional<Source> source = Source.getSource(article.get().getSourceId());
                if(source.isPresent()) {
                    final List<String> sortBys = source.get().getSortBysAvailable();
                    final Optional<String> sortBy = sortBys.stream()
                            .skip(ThreadLocalRandom.current().nextInt(sortBys.size()))
                            .findAny();
                    if(sortBy.isPresent()) {
                        final List<Article> articles = Article.getArticles(source.get(), sortBy.get());
                        final Optional<Article> a = articles.stream()
                                .skip(ThreadLocalRandom.current().nextInt(articles.size()))
                                .findAny();
                        if(a.isPresent()) {
                            return ok(JsonRecommendView.render(Status.OK, "", Collections.singletonList(a.get()))).as("application/json; charset=UTF-8");
                        }
                    }
                }
            }
        }

        return ok(JsonRecommendView.render(Status.OK, "Favorite an article to start getting recommendations.", new ArrayList<>()));
    }
}
