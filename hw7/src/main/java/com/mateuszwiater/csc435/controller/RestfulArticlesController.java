package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.Article;
import com.mateuszwiater.csc435.model.Category;
import com.mateuszwiater.csc435.model.Source;
import com.mateuszwiater.csc435.view.ArticlesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.mateuszwiater.csc435.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.util.Response.Status.OK;

@SuppressWarnings("Duplicates")
public class RestfulArticlesController {
    private static Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    public static Route GET = (req, resp) -> {
        final String sourceParam = req.queryParams("source");
        final String sortByParam = req.queryParams("sortBy");
        final String categoryParam = req.queryParams("category");

        try {
            if(sourceParam != null && !sourceParam.isEmpty()) {
                final Optional<Source> source = Source.getSource(sourceParam);
                if(source.isPresent()) {
                    if(sortByParam != null && !sortByParam.isEmpty()) {
                        if(source.get().getSortBysAvailable().contains(sortByParam)) {
                            return ArticlesView.getView(OK, "", Article.getArticles(source.get(), sortByParam));
                        } else {
                            resp.status(400);
                            return ArticlesView.getView(FAIL, String.format("Invalid SortBy '%s' For Source '%s'",sortByParam,source.get().getId()), new ArrayList<>());
                        }
                    } else {
                        final List<String> sortBysAvailable = source.get().getSortBysAvailable();
                        String sortBy = sortBysAvailable.get(ThreadLocalRandom.current().nextInt(sortBysAvailable.size()));
                        return ArticlesView.getView(OK, "", Article.getArticles(source.get(), sortBy));
                    }
                } else {
                    resp.status(400);
                    return ArticlesView.getView(FAIL, "Invalid Source Id.", new ArrayList<>());
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
                                    return ArticlesView.getView(OK, String.format("No Source That Matches The Category '%s' And SortBy '%s'",category.get().getName(),sortByParam), new ArrayList<>());
                                } else {
                                    final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()));
                                    final List<Article> articles = Article.getArticles(source, sortByParam);
                                    return ArticlesView.getView(OK, "", articles);
                                }
                            } else {
                                resp.status(400);
                                return ArticlesView.getView(FAIL, "Invalid SortBy '" + sortByParam + "'", new ArrayList<>());
                            }
                        } else {
                            final List<Source> sources = Source.getSources(category.get(), null, null);

                            if(sources.size() == 0) {
                                return ArticlesView.getView(OK, String.format("No Source That Matches The Category '%s'",category.get().getName()), new ArrayList<>());
                            } else {
                                final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()));
                                final String sortBy = source.getSortBysAvailable().get(ThreadLocalRandom.current().nextInt(source.getSortBysAvailable().size()));
                                final List<Article> articles = Article.getArticles(source, sortBy);
                                return ArticlesView.getView(OK, "", articles);
                            }
                        }
                    } else {
                        resp.status(400);
                        return ArticlesView.getView(FAIL, "Invalid Category.", new ArrayList<>());
                    }
                } else {
                    final List<Category> categories = Category.getCategories();
                    if(categories.size() == 0) {
                        return ArticlesView.getView(FAIL, "No Available Categories", new ArrayList<>());
                    } else {
                        final Category category = categories.get(ThreadLocalRandom.current().nextInt(categories.size()));
                        if(sortByParam != null && !sortByParam.isEmpty()) {
                            if(validateSortBy(sortByParam)) {
                                final List<Source> sources = Source.getSources(category, null, null).stream()
                                        .filter(s -> s.getSortBysAvailable().contains(sortByParam))
                                        .collect(Collectors.toList());

                                if(sources.size() == 0) {
                                    return ArticlesView.getView(OK, String.format("No Source That Matches The Category '%s' And SortBy '%s'",category.getName(),sortByParam), new ArrayList<>());
                                } else {
                                    final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()));
                                    final List<Article> articles = Article.getArticles(source, sortByParam);
                                    return ArticlesView.getView(OK, "", articles);
                                }
                            } else {
                                resp.status(400);
                                return ArticlesView.getView(FAIL, "Invalid SortBy '" + sortByParam + "'", new ArrayList<>());
                            }
                        } else {
                            final List<Source> sources = Source.getSources(category, null, null);

                            if(sources.size() == 0) {
                                return ArticlesView.getView(OK, String.format("No Source That Matches The Category '%s'",category.getName()), new ArrayList<>());
                            } else {
                                final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()));
                                final String sortBy = source.getSortBysAvailable().get(ThreadLocalRandom.current().nextInt(source.getSortBysAvailable().size()));
                                final List<Article> articles = Article.getArticles(source, sortBy);
                                return ArticlesView.getView(OK, "", articles);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("ARTICLES GET", e);
            resp.status(500);
            return ArticlesView.getView(FAIL, "Internal Server Error", new ArrayList<>());
        }
    };

    private static boolean validateSortBy(final String sortBy) {
        return Arrays.asList("top","latest", "popular").contains(sortBy);
    }
}
