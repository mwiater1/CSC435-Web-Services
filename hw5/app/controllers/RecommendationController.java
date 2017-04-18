package controllers;

import actions.Auth;
import com.google.common.collect.Lists;
import models.Article;
import models.Preference;
import models.Source;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.RecommendationView;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@With(Auth.class)
@SuppressWarnings("Duplicates")
public class RecommendationController extends Controller {

    public Result doGet() {
        User user = User.getUser(UUID.fromString(session("apiKey"))).get();

        Map<Long, Preference> preferenceMap = user.getPreferences().stream()
                .collect(Collectors.toMap(Preference::getArticleId, Function.identity()));

        List<Preference> favorites = preferenceMap.values()
                .stream().filter(Preference::isFavorite).collect(Collectors.toList());

        final List<Article> articles = new ArrayList<>();

        if(favorites.size() != 0) {
            Preference p = favorites.get(ThreadLocalRandom.current().nextInt(favorites.size()));
            Optional<Article> a = Article.getArticle(p.getArticleId());
            if(a.isPresent()) {
                Source s = a.get().getSource();
                    final List<String> sortBysAvailable = s.getSortBysAvailable();
                    final String sortBy = sortBysAvailable.get(ThreadLocalRandom.current().nextInt(sortBysAvailable.size()));
                    List<Article> al = Article.getArticles(s, sortBy).stream()
                            .filter(ar -> !Objects.equals(ar.getId(), p.getArticleId()))
                            .collect(Collectors.toList());

                    articles.add(al.get(ThreadLocalRandom.current().nextInt(al.size())));
            }
        }

        return ok(RecommendationView.render(articles, preferenceMap));
    }
}
