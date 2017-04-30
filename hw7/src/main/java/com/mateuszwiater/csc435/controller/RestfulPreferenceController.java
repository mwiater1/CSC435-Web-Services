package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Preference;
import com.mateuszwiater.csc435.model.User;
import com.mateuszwiater.csc435.view.PreferenceView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mateuszwiater.csc435.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.util.Response.Status.OK;

@SuppressWarnings("Duplicates")
public class RestfulPreferenceController {
    private static Logger logger = LoggerFactory.getLogger(PreferenceController.class);

    public static Route GET = (req, resp) -> {
        final boolean read = Boolean.valueOf(req.queryParams("read"));
        final boolean favorite = Boolean.valueOf(req.queryParams("favorite"));
        final String apiKey = ((User)req.session().attribute("user")).getApiKey();

        try {
            final List<Preference> preferences = Preference.getPreferences(apiKey).stream()
                    .filter(p -> (p.isFavorite() && favorite) || (p.isRead() && read))
                    .collect(Collectors.toList());

            final List<Article> articles = new ArrayList<>();

            for (Preference preference : preferences) {
                Article.getArticle(preference.getArticleId()).ifPresent(articles::add);
            }

            return PreferenceView.getView(OK, "", articles);
        } catch (SQLException e) {
            logger.error("PREFERENCE GET", e);
            resp.status(500);
            return PreferenceView.getView(FAIL, "Internal Server Error", new ArrayList<>());
        }
    };

    public static Route POST = (req, resp) -> {
        final String readParam = req.queryParams("read");
        final String favoriteParam = req.queryParams("favorite");
        final String articleIdParam = req.queryParams("articleId");
        final String apiKey = ((User)req.session().attribute("user")).getApiKey();

        try {
            Optional<Article> article = Article.getArticle(articleIdParam);

            if(article.isPresent()) {
                if(favoriteParam != null || readParam != null) {
                    final Preference preference = Preference.getPreference(apiKey, articleIdParam).orElse(new Preference(apiKey, articleIdParam, false, false));

                    if (favoriteParam != null) {
                        preference.setFavorite(Boolean.valueOf(favoriteParam));
                    }

                    if (readParam != null) {
                        preference.setRead(Boolean.valueOf(readParam));
                    }

                    preference.save();
                }
                return PreferenceView.postView(OK, "");
            } else {
                return PreferenceView.postView(FAIL, "Invalid Article ID: '" + articleIdParam + "'");
            }
        } catch (SQLException e) {
            logger.error("PREFERENCE POST", e);
            resp.status(500);
            return PreferenceView.postView(FAIL, "Internal Server Error");
        }
    };
}
