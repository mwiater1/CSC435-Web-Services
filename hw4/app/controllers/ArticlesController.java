package controllers;

import models.Article;
import models.Category;
import models.Preference;
import models.Source;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ArticlesView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArticlesController extends Controller {

    public Result doGet() {
        final Map<String, Preference> preferenceMap = Preference.getPreferences().stream()
                .collect(Collectors.toMap(Preference::getArticleId, Function.identity()));

        final List<String> sortBys = Arrays.asList("top", "latest", "popular");

        return ok(ArticlesView.render(Article.getArticles(), preferenceMap, Source.getSources(),
                Category.getCategories(), sortBys, "abc-news-au","",""));
    }
}
