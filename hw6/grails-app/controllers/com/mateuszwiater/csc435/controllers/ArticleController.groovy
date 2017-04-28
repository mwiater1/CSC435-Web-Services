package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.Preference
import com.mateuszwiater.csc435.domains.Source
import com.mateuszwiater.csc435.domains.Category
import com.mateuszwiater.csc435.domains.User
import com.mateuszwiater.csc435.services.ArticleService
import com.mateuszwiater.csc435.services.CategoryService
import com.mateuszwiater.csc435.services.PreferenceService
import com.mateuszwiater.csc435.services.SourceService

import java.util.concurrent.ThreadLocalRandom

class ArticleController {
    SourceService sourceService
    CategoryService categoryService
    PreferenceService preferenceService
    ArticleService articleService


    def doGet() {
        String sourceParam = formatParam(params.source)
        String sortByParam = formatParam(params.sortby)
        String categoryParam = formatParam(params.category)

        Source source
        String sortBy
        Optional<Source> src = sourceService.getSource(sourceParam)
        Optional<Category> cat = categoryService.getCategory(categoryParam)

        // If source exists
        if(src.isPresent()) {
            source = src.get()
        } else {
            Category category
            // Choose the category
            if(cat.isPresent()) {
                category = cat.get()
            } else {
                List<Category> categories = categoryService.getCategories()
                category = categories.get(ThreadLocalRandom.current().nextInt(categories.size()))
            }

            List<Source> sources = sourceService.getSources(category, null, null)
            source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()))
        }

        // Choose the sortBy
        if(source.getSortBysAvailable().contains(sortByParam)) {
            sortBy = sortByParam
        } else {
            sortBy = source.getSortBysAvailable().get(ThreadLocalRandom.current().nextInt(source.getSortBysAvailable().size()))
        }

        // Build preference map
        Map<Long, Preference> preferenceMap = preferenceService.getPreferences(((User)session["user"]).getApiKey())
                .collectEntries {[it.articleId, it]}

        [articles: articleService.getArticles(source, sortBy), preferenceMap: preferenceMap,
         sources: sourceService.getSources(null, null, null),
         categories: categoryService.getCategories(), sortBys: Arrays.asList("top", "latest", "popular"),
         selectedSource: source.getId(), selectedCategory: source.getCategory(), selectedSortBy: sortBy]
    }

    static String formatParam(Object param) {
        return Optional.ofNullable(param).orElse("any")
    }
}
