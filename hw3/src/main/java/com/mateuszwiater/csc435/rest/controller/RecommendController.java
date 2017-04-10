package com.mateuszwiater.csc435.rest.controller;

import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Preference;
import com.mateuszwiater.csc435.model.Source;
import com.mateuszwiater.csc435.rest.util.Auth;
import com.mateuszwiater.csc435.rest.view.RecommendView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.mateuszwiater.csc435.rest.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.rest.util.Response.Status.OK;

public class RecommendController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(RecommendController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String apiKey = req.getParameter("apiKey");
        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try {
                final Auth.AuthResponse authResponse = Auth.authenticate(apiKey);
                if (authResponse.isAuthenticated()) {
                    final List<Preference> preferences = Preference.getPreferences(apiKey).stream()
                            .filter(Preference::isFavorite)
                            .collect(Collectors.toList());
                    final Optional<Preference> preference = preferences.stream()
                            .skip(ThreadLocalRandom.current().nextInt(preferences.size()))
                            .findAny();
                    if(preference.isPresent()) {
                        final Optional<Article> article = Article.getArticle(preference.get().getArticleId());
                        if(article.isPresent()) {
                            final Optional<Source> source = Source.getSource(article.get().getSource());
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
                                        out.print(RecommendView.getView(OK, "", a.get()));
                                        return;
                                    }
                                }
                            }
                        }
                    }

                    out.print(RecommendView.getView(OK, "Favorite an article to start getting recommendations.", null));
                } else {
                    resp.setStatus(400);
                    out.print(authResponse.getView());
                }
            } catch (SQLException e) {
                logger.error("SOURCES GET", e);
                resp.setStatus(500);
                out.print(RecommendView.getView(FAIL, "Internal Server Error", null));
            }
        } catch (IOException e) {
            logger.error("SOURCES GET", e);
        }
    }
}
