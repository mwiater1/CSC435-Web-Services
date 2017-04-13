package controllers;

import models.Article;
import models.Preference;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.RecommendationView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecommendationController extends Controller {

    public Result doGet() {
        List<Article> articles = Article.getArticles();
        articles = Collections.singletonList(articles.get(ThreadLocalRandom.current().nextInt(articles.size())));

        final Map<String, Preference> preferenceMap = Preference.getPreferences().stream()
                .collect(Collectors.toMap(Preference::getArticleId, Function.identity()));

        return ok(RecommendationView.render(articles, preferenceMap));
    }
}
