package com.mateuszwiater.csc435.controller;

import com.google.common.collect.Lists;
import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Preference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PreferenceController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(PreferenceController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean favoriteParam = formatParameter(req.getParameter("favorite"));
        boolean readParam = formatParameter(req.getParameter("read"));
        String apiKey = (String) req.getSession().getAttribute("apiKey");

        if(apiKey == null) {
            resp.sendRedirect("/hw2");
        } else {
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

                req.getSession().setAttribute("favorite", favoriteParam);
                req.getSession().setAttribute("read", readParam);
                req.getSession().setAttribute("articles", Lists.partition(articles,3));
                req.getSession().setAttribute("preferenceMap", preferenceMap);
                req.getRequestDispatcher("/view/PreferenceView.jsp").forward(req, resp);
            } catch (SQLException e) {
                logger.error("PREFERENCE GET", e);
                req.getSession().removeAttribute("apiKey");
                resp.sendRedirect("/hw2");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String favoriteParam = req.getParameter("favorite");
        String readParam = req.getParameter("read");
        String articleIdParam = req.getParameter("articleId");
        String apiKey = (String) req.getSession().getAttribute("apiKey");

        if(apiKey == null) {
            resp.sendRedirect("/hw2");
        } else {
            if(articleIdParam == null || articleIdParam.isEmpty()) {
                resp.sendError(400);
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
                } catch (SQLException e) {
                    logger.error("PREFERENCE POST", e);
                    req.getSession().removeAttribute("apiKey");
                    resp.sendError(500);
                    resp.sendRedirect("/hw2");
                }
            }
        }
    }

    private boolean formatParameter(final String param) {
        return param == null || param.isEmpty() ? false : Boolean.valueOf(param);
    }
}
