package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.Article
import com.mateuszwiater.csc435.domains.Source
import com.mateuszwiater.csc435.domains.Category
import com.mateuszwiater.csc435.services.ArticleService
import com.mateuszwiater.csc435.services.CategoryService
import com.mateuszwiater.csc435.services.SourceService

import java.util.concurrent.ThreadLocalRandom

class RestfulArticleController {
    SourceService sourceService
    ArticleService articleService
    CategoryService categoryService

    def doGet() {
        String sourceParam = params.source
        String sortByParam = params.sortby
        String categoryParam = params.category

        if(sourceParam != null && !sourceParam.isEmpty()) {
            final Optional<Source> source = sourceService.getSource(sourceParam)
            if(source.isPresent()) {
                if(sortByParam != null && !sortByParam.isEmpty()) {
                    if(source.get().getSortBysAvailable().contains(sortByParam)) {
                        [status: "ok", data: articleService.getArticles(source.get(), sortByParam)]
                    } else {
                        response.status = 400
                        [status: "fail", message: "Invalid SortBy '$sortByParam' For Source '${source.get().getName()}'", data: new ArrayList<>()]
                    }
                } else {
                    final List<String> sortBysAvailable = source.get().getSortBysAvailable()
                    String sortBy = sortBysAvailable.get(ThreadLocalRandom.current().nextInt(sortBysAvailable.size()))
                    [status: "ok", data: articleService.getArticles(source.get(), sortBy)]
                }
            } else {
                response.status = 400
                [status: "fail", message: "Invalid Source Id.", data: new ArrayList<>()]
            }
        } else {
            if(categoryParam != null && !categoryParam.isEmpty()) {
                Optional<Category> category = categoryService.getCategory(categoryParam)
                if(category.isPresent()) {
                    if(sortByParam != null && !sortByParam.isEmpty()) {
                        if(validateSortBy(sortByParam)) {
                            final List<Source> sources = sourceService.getSources(category.get(), null, null).stream()
                                    .filter{s -> s.getSortBysAvailable().contains(sortByParam)}
                                    .collect()

                            if(sources.size() == 0) {
                                [status: "ok", message: "No Source That Matches The Category '${category.get().getName()}' And SortBy '$sortByParam'", data: new ArrayList<>()]
                            } else {
                                final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()))
                                final List<Article> articles = articleService.getArticles(source, sortByParam)
                                [status: "ok", data: articles]
                            }
                        } else {
                            response.status = 400
                            [status: "fail", message: "Invalid SortBy '$sortByParam'", data: new ArrayList<>()]
                        }
                    } else {
                        final List<Source> sources = sourceService.getSources(category.get(), null, null)

                        if(sources.size() == 0) {
                            [status: "ok", message: "No Source that Matches The Category '${category.get().getName()}'", data: new ArrayList<>()]
                        } else {
                            final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()))
                            final String sortBy = source.getSortBysAvailable().get(ThreadLocalRandom.current().nextInt(source.getSortBysAvailable().size()))
                            final List<Article> articles = articleService.getArticles(source, sortBy)
                            [status: "ok", data: articles]
                        }
                    }
                } else {
                    response.status = 400
                    [status: "fail", message: "Invalid Category.", data: new ArrayList<>()]
                }
            } else {
                final List<Category> categories = categoryService.getCategories()
                if(categories.size() == 0) {
                    response.status = 400
                    [status: "fail", message: "No Available Categories", data: new ArrayList<>()]
                } else {
                    final Category category = categories.get(ThreadLocalRandom.current().nextInt(categories.size()))
                    if(sortByParam != null && !sortByParam.isEmpty()) {
                        if(validateSortBy(sortByParam)) {
                            final List<Source> sources = sourceService.getSources(category, null, null).stream()
                                    .filter{s -> s.getSortBysAvailable().contains(sortByParam)}
                                    .collect()

                            if(sources.size() == 0) {
                                [status: "ok", message: "No Source That Matches The Category '${category.getName()}' And SortBy '$sortByParam'", data: new ArrayList<>()]
                            } else {
                                final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()))
                                final List<Article> articles = articleService.getArticles(source, sortByParam)
                                [status: "ok", data: articles]
                            }
                        } else {
                            response.status = 400
                            [status: "fail", message: "Invalid SortBy '$sortByParam'", data: new ArrayList<>()]
                        }
                    } else {
                        final List<Source> sources = sourceService.getSources(category, null, null)

                        if(sources.size() == 0) {
                            [status: "ok", message: "No Source That Matches The Category '${category.getName()}'", data: new ArrayList<>()]
                        } else {
                            final Source source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()))
                            final String sortBy = source.getSortBysAvailable().get(ThreadLocalRandom.current().nextInt(source.getSortBysAvailable().size()))
                            final List<Article> articles = articleService.getArticles(source, sortBy)
                            [status: "ok", data: articles]
                        }
                    }
                }
            }
        }
    }

    private boolean validateSortBy(final String sortBy) {
        ["top", "latest", "popular"].contains(sortBy)
    }
}
