package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Preference;
import com.mateuszwiater.csc435.model.Source;
import com.mateuszwiater.csc435.model.User;
import com.mateuszwiater.csc435.view.RecommendView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.mateuszwiater.csc435.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.util.Response.Status.OK;

public class RestfulRecommendController {
    private static Logger logger = LoggerFactory.getLogger(RestfulRecommendController.class);

    public static Route GET = (req, resp) -> {
        final String apiKey = ((User)req.session().attribute("user")).getApiKey();

        try {
            final List<Preference> preferences = Preference.getPreferences(apiKey).stream()
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
                                return RecommendView.getView(OK, "", a.get());
                            }
                        }
                    }
                }
            }

            return RecommendView.getView(OK, "Favorite an article to start getting recommendations.", null);
        } catch (SQLException e) {
            logger.error("SOURCES GET", e);
            resp.status(500);
            return RecommendView.getView(FAIL, "Internal Server Error", null);
        }
    };
}
