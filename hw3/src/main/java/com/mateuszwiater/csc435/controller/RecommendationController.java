package com.mateuszwiater.csc435.controller;

import com.google.common.collect.Lists;
import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Preference;
import com.mateuszwiater.csc435.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class RecommendationController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(RecommendationController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String apiKey = (String) req.getSession().getAttribute("apiKey");

        if(apiKey == null) {
            resp.sendRedirect("/hw3");
        } else {
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
                        Optional<Source> s = Source.getSource(a.get().getSource());
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

                req.getSession().setAttribute("articles", Lists.partition(articles,3));
                req.getSession().setAttribute("preferenceMap", preferenceMap);
                req.getRequestDispatcher("/view/RecommendationView.jsp").forward(req, resp);
            } catch (SQLException e) {
                logger.error("RECOMMENDATION GET", e);
                req.getSession().removeAttribute("apiKey");
                resp.sendRedirect("/hw3");
            }
        }
    }
}
