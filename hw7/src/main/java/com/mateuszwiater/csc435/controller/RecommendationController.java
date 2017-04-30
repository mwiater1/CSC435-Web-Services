package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Preference;
import com.mateuszwiater.csc435.model.Source;
import com.mateuszwiater.csc435.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mateuszwiater.csc435.util.Util.render;

@SuppressWarnings("Duplicates")
public class RecommendationController {
    private static Logger logger = LoggerFactory.getLogger(RecommendationController.class);

    public static Route GET = (req, resp) -> {
        String apiKey = ((User)req.session().attribute("user")).getApiKey();

        try {
            Map<String,Preference> preferenceMap = Preference.getPreferences(apiKey).stream()
                    .collect(Collectors.toMap(Preference::getArticleId, Function.identity()));

            List<Preference> favorites = preferenceMap.values()
                    .stream().filter(Preference::isFavorite).collect(Collectors.toList());

            final List<Article> articles = new ArrayList<>();

            if(favorites.size() != 0) {
                Preference p = favorites.get(ThreadLocalRandom.current().nextInt(favorites.size()));
                Optional<Article> a = Article.getArticle(p.getArticleId());
                if(a.isPresent()) {
                    Optional<Source> s = Source.getSource(a.get().getSourceId());
                    if(s.isPresent()) {
                        final List<String> sortBysAvailable = s.get().getSortBysAvailable();
                        final String sortBy = sortBysAvailable.get(ThreadLocalRandom.current().nextInt(sortBysAvailable.size()));
                        List<Article> al = Article.getArticles(s.get(), sortBy).stream()
                                .filter(ar -> !Objects.equals(ar.getId(), p.getArticleId()))
                                .collect(Collectors.toList());

                        articles.add(al.get(ThreadLocalRandom.current().nextInt(al.size())));
                    }
                }
            }

            Map<String, Object> model = new HashMap<>();
            model.put("articles", articles);
            model.put("currentPage", "recommend");
            model.put("preferenceMap", preferenceMap);

            return render(model, "recommendView.ftl");
        } catch (SQLException e) {
            logger.error("RECOMMENDATION GET", e);
            resp.status(500);
            return "Internal Server Error.";
        }
    };
}
