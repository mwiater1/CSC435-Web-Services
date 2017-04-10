package com.mateuszwiater.csc435.rest.controller;

import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Preference;
import com.mateuszwiater.csc435.rest.util.Auth;
import com.mateuszwiater.csc435.rest.view.PreferenceView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mateuszwiater.csc435.rest.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.rest.util.Response.Status.OK;

@SuppressWarnings("Duplicates")
public class PreferenceController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(PreferenceController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final boolean read = Boolean.valueOf(req.getParameter("read"));
        final boolean favorite = Boolean.valueOf(req.getParameter("favorite"));
        final String apiKey = req.getParameter("apiKey");

        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try {
                final Auth.AuthResponse authResponse = Auth.authenticate(apiKey);
                if (authResponse.isAuthenticated()) {

                    final List<Preference> preferences = Preference.getPreferences(apiKey).stream()
                            .filter(p -> (p.isFavorite() && favorite) || (p.isRead() && read))
                            .collect(Collectors.toList());

                    final List<Article> articles = new ArrayList<>();

                    for (Preference preference : preferences) {
                        Article.getArticle(preference.getArticleId()).ifPresent(articles::add);
                    }

                    out.print(PreferenceView.getView(OK, "", articles));
                } else {
                    resp.setStatus(400);
                    out.print(authResponse.getView());
                }
            } catch (SQLException e) {
                logger.error("PREFERENCE GET", e);
                resp.setStatus(500);
                out.print(PreferenceView.getView(FAIL, "Internal Server Error", new ArrayList<>()));
            }
        } catch (IOException e) {
            logger.error("PREFERENCE GET", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String readParam = req.getParameter("read");
        final String favoriteParam = req.getParameter("favorite");
        final String articleIdParam = req.getParameter("articleId");
        final String apiKey = req.getParameter("apiKey");

        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try {
                final Auth.AuthResponse authResponse = Auth.authenticate(apiKey);
                if (authResponse.isAuthenticated()) {

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
                            out.print(PreferenceView.postView(OK, ""));
                    } else {
                        out.print(PreferenceView.postView(FAIL, "Invalid Article ID: '" + articleIdParam + "'"));
                    }
                } else {
                    resp.setStatus(400);
                    out.print(authResponse.getView());
                }
            } catch (SQLException e) {
                logger.error("PREFERENCE POST", e);
                resp.setStatus(500);
                out.print(PreferenceView.postView(FAIL, "Internal Server Error"));
            }
        } catch (IOException e) {
            logger.error("PREFERENCE POST", e);
        }
    }
}
