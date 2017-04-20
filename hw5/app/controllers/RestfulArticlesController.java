package controllers;

import actions.RestfulAuth;
import com.google.common.collect.Lists;
import models.Article;
import models.Category;
import models.Source;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.Status;
import views.html.JsonArticlesView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@With(RestfulAuth.class)
public class RestfulArticlesController extends Controller {

    public Result doGet() {
        String sourceParam = request().getQueryString("source");
        String sortByParam = request().getQueryString("sortby");
        String categoryParam = request().getQueryString("category");

        Result result;

        if(sourceParam != null && !sourceParam.isEmpty()) {
            final Optional<Source> source = Source.getSource(sourceParam);
            if(source.isPresent()) {
                if(sortByParam != null && !sortByParam.isEmpty()) {
                    if(source.get().getSortBysAvailable().contains(sortByParam)) {
                        result = ok(JsonArticlesView.render(Status.OK, "", Article.getArticles(source.get(), sortByParam)));
                    } else {
                        result = status(400, JsonArticlesView.render(Status.FAIL, String.format("Invalid SortBy '%s' For Source '%s'",sortByParam,source.get().getId()), new ArrayList<>()));
                    }
                } else {
                    final List<String> sortBysAvailable = source.get().getSortBysAvailable();
                    String sortBy = sortBysAvailable.get(ThreadLocalRandom.current().nextInt(sortBysAvailable.size()));
                    result = ok(JsonArticlesView.render(Status.OK, "", Article.getArticles(source.get(), sortBy)));
                }
            } else {
                result = status(400, JsonArticlesView.render(Status.FAIL, "Invalid Source Id.", new ArrayList<>()));
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
                                result = ok(JsonArticlesView.render(Status.OK, String.format("No Source That Matches The Category '%s' And SortBy '%s'",category.get().getName(),sortByParam), new ArrayList<>()));
                            } else {
                                final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()));
                                final List<Article> articles = Article.getArticles(source, sortByParam);
                                result = ok(JsonArticlesView.render(Status.OK, "", articles));
                            }
                        } else {
                            result = status(400, JsonArticlesView.render(Status.FAIL, "Invalid SortBy '" + sortByParam + "'", new ArrayList<>()));
                        }
                    } else {
                        final List<Source> sources = Source.getSources(category.get(), null, null);

                        if(sources.size() == 0) {
                            result = ok(JsonArticlesView.render(Status.OK, String.format("No Source That Matches The Category '%s'",category.get().getName()), new ArrayList<>()));
                        } else {
                            final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()));
                            final String sortBy = source.getSortBysAvailable().get(ThreadLocalRandom.current().nextInt(source.getSortBysAvailable().size()));
                            final List<Article> articles = Article.getArticles(source, sortBy);
                            result = ok(JsonArticlesView.render(Status.OK, "", articles));
                        }
                    }
                } else {
                    result = status(400, JsonArticlesView.render(Status.FAIL, "Invalid Category.", new ArrayList<>()));
                }
            } else {
                final List<Category> categories = Category.getCategories();
                if(categories.size() == 0) {
                    result = ok(JsonArticlesView.render(Status.FAIL, "No Available Categories", new ArrayList<>()));
                } else {
                    final Category category = categories.get(ThreadLocalRandom.current().nextInt(categories.size()));
                    if(sortByParam != null && !sortByParam.isEmpty()) {
                        if(validateSortBy(sortByParam)) {
                            final List<Source> sources = Source.getSources(category, null, null).stream()
                                    .filter(s -> s.getSortBysAvailable().contains(sortByParam))
                                    .collect(Collectors.toList());

                            if(sources.size() == 0) {
                                result = ok(JsonArticlesView.render(Status.OK, String.format("No Source That Matches The Category '%s' And SortBy '%s'",category.getName(),sortByParam), new ArrayList<>()));
                            } else {
                                final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()));
                                final List<Article> articles = Article.getArticles(source, sortByParam);
                                result = ok(JsonArticlesView.render(Status.OK, "", articles));
                            }
                        } else {
                            result = status(400, JsonArticlesView.render(Status.FAIL, "Invalid SortBy '" + sortByParam + "'", new ArrayList<>()));
                        }
                    } else {
                        final List<Source> sources = Source.getSources(category, null, null);

                        if(sources.size() == 0) {
                            result = ok(JsonArticlesView.render(Status.OK, String.format("No Source That Matches The Category '%s'",category.getName()), new ArrayList<>()));
                        } else {
                            final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()));
                            final String sortBy = source.getSortBysAvailable().get(ThreadLocalRandom.current().nextInt(source.getSortBysAvailable().size()));
                            final List<Article> articles = Article.getArticles(source, sortBy);
                            result = ok(JsonArticlesView.render(Status.OK, "", articles));
                        }
                    }
                }
            }
        }

        return result.as("application/json; charset=UTF-8");
    }

    private boolean validateSortBy(final String sortBy) {
        return Lists.asList("top","latest", new String[]{"popular"}).contains(sortBy);
    }
}
