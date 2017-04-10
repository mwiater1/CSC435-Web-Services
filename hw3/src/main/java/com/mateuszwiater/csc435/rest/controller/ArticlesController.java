package com.mateuszwiater.csc435.rest.controller;

import com.google.common.collect.Lists;
import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Category;
import com.mateuszwiater.csc435.model.Source;
import com.mateuszwiater.csc435.rest.util.Auth;
import com.mateuszwiater.csc435.rest.view.ArticlesView;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.mateuszwiater.csc435.rest.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.rest.util.Response.Status.OK;

public class ArticlesController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String apiKey = req.getParameter("apiKey");
        final String sourceParam = req.getParameter("source");
        final String sortByParam = req.getParameter("sortBy");
        final String categoryParam = req.getParameter("category");

        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try {
                Auth.AuthResponse authResponse = Auth.authenticate(apiKey);
                if (authResponse.isAuthenticated()) {
                    if(sourceParam != null && !sourceParam.isEmpty()) {
                        final Optional<Source> source = Source.getSource(sourceParam);
                        if(source.isPresent()) {
                            if(sortByParam != null && !sortByParam.isEmpty()) {
                                if(source.get().getSortBysAvailable().contains(sortByParam)) {
                                    out.print(ArticlesView.getView(OK, "", Article.getArticles(source.get(), sortByParam)));
                                } else {
                                    resp.setStatus(400);
                                    out.print(ArticlesView.getView(FAIL, String.format("Invalid SortBy '%s' For Source '%s'",sortByParam,source.get().getId()), new ArrayList<>()));
                                }
                            } else {
                                final List<String> sortBysAvailable = source.get().getSortBysAvailable();
                                String sortBy = sortBysAvailable.get(ThreadLocalRandom.current().nextInt(sortBysAvailable.size()));
                                out.print(ArticlesView.getView(OK, "", Article.getArticles(source.get(), sortBy)));
                            }
                        } else {
                            resp.setStatus(400);
                            out.print(ArticlesView.getView(FAIL, "Invalid Source Id.", new ArrayList<>()));
                        }
                    } else {
                        if(categoryParam != null && !categoryParam.isEmpty()) {
                            Optional<Category> category = Category.getCategory(categoryParam);
                            if(category.isPresent()) {
                                if(sortByParam != null && !sortByParam.isEmpty()) {
                                    if(validateSortBy(sortByParam)) {
                                        final List<Source> sources = Source.getSources(category.get(), null, null).stream()
                                                .filter(s -> s.getSortBysAvailable().contains(sortByParam))
                                                .collect(Collectors.toList());

                                        if(sources.size() == 0) {
                                            out.print(ArticlesView.getView(OK,"No Source That Matches "));
                                        } else {

                                        }
                                    } else {
                                        resp.setStatus(400);
                                        out.print(ArticlesView.getView(FAIL, "Invalid SortBy '" + sortByParam + "'", new ArrayList<>()));
                                    }
                                } else {

                                }
                            } else {
                                resp.setStatus(400);
                                out.print(ArticlesView.getView(FAIL, "Invalid Category.", new ArrayList<>()));
                            }
                        } else {

                        }
                    }
                } else {
                    resp.setStatus(400);
                    out.print(authResponse.toJson());
                }
            } catch (SQLException e) {
                logger.error("ARTICLES GET", e);
                resp.setStatus(500);
                out.print(ArticlesView.getView(FAIL, "Internal Server Error", new ArrayList<>()));
            }
        } catch (IOException e) {
            logger.error("ARTICLES GET", e);
        }
    }

    private boolean validateSortBy(final String sortBy) {
        return Lists.asList("top","latest", new String[]{"popular"}).contains(sortBy);
    }
}
