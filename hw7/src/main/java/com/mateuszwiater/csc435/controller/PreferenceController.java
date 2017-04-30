package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Preference;
import com.mateuszwiater.csc435.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mateuszwiater.csc435.util.Util.render;

@SuppressWarnings("Duplicates")
public class PreferenceController {
    private static Logger logger = LoggerFactory.getLogger(PreferenceController.class);

    public static Route GET = (req, resp) -> {
        boolean favoriteParam = formatParameter(req.queryParams("favorite"));
        boolean readParam = formatParameter(req.queryParams("read"));
        String apiKey = ((User) req.session().attribute("user")).getApiKey();

        try {
            // Build preference map
            Map<String,Preference> preferenceMap = Preference.getPreferences(apiKey).stream().collect(Collectors.toMap(Preference::getArticleId, Function.identity()));

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

            Map<String, Object> model = new HashMap<>();
            model.put("currentPage", "preference");
            model.put("favorite", favoriteParam);
            model.put("read", readParam);
            model.put("articles", articles);
            model.put("preferenceMap", preferenceMap);

            return render(model, "preferenceView.ftl");
        } catch (SQLException e) {
            logger.error("PREFERENCE GET", e);
            resp.status(500);
            return "Internal Server Error.";
        }
    };

    public static Route POST = (req, resp) -> {
        String favoriteParam = req.queryParams("favorite");
        String readParam = req.queryParams("read");
        String articleIdParam = req.queryParams("articleId");
        String apiKey = ((User) req.session().attribute("user")).getApiKey();

        if(articleIdParam == null || articleIdParam.isEmpty()) {
            resp.status(400);
            return "Missing Parameters!";
        } else {
            try {
                if(favoriteParam != null || readParam != null) {
                    final Preference preference = Preference.getPreference(apiKey, articleIdParam).orElse(new Preference(apiKey,articleIdParam, false, false));

                    if(favoriteParam != null) {
                        preference.setFavorite(Boolean.valueOf(favoriteParam));
                    }

                    if(readParam != null) {
                        preference.setRead(Boolean.valueOf(readParam));
                    }

                    preference.save();
                }

                return "";
            } catch (SQLException e) {
                logger.error("PREFERENCE POST", e);
                resp.status(500);
                return "Internal Server Error";
            }
        }
    };

    private static boolean formatParameter(final String param) {
        return param == null || param.isEmpty() ? false : Boolean.valueOf(param);
    }
}
