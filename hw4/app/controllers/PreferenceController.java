package controllers;

import models.Article;
import models.Preference;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.PreferenceView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PreferenceController extends Controller {

    public Result doGet() {
        final Map<String, Preference> preferenceMap = Preference.getPreferences().stream().filter(Preference::isFavorite)
                .collect(Collectors.toMap(Preference::getArticleId, Function.identity()));

        final List<Article> articles = Article.getArticles().stream()
                .filter(a -> preferenceMap.containsKey(a.getId()))
                .collect(Collectors.toList());

        return ok(PreferenceView.render(articles, preferenceMap, true, false));
    }
}
