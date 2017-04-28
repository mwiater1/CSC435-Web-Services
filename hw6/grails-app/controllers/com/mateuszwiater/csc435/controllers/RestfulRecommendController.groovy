package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.Article
import com.mateuszwiater.csc435.domains.Preference
import com.mateuszwiater.csc435.domains.Source
import com.mateuszwiater.csc435.domains.User
import com.mateuszwiater.csc435.services.ArticleService
import com.mateuszwiater.csc435.services.PreferenceService
import com.mateuszwiater.csc435.services.SourceService

import java.util.concurrent.ThreadLocalRandom

class RestfulRecommendController {
    PreferenceService preferenceService
    SourceService sourceService
    ArticleService articleService

    def doGet() {
        User user = session["user"] as User

        final List<Preference> preferences = preferenceService.getPreferences(user.getApiKey()).stream()
                .filter{ p -> p.isFavorite() }
                .collect()
        final Optional<Preference> preference = preferences.stream()
                .skip(ThreadLocalRandom.current().nextInt(preferences.size()))
                .findAny()
        if(preference.isPresent()) {
            final Optional<Article> article = articleService.getArticle(preference.get().getArticleId())
            if(article.isPresent()) {
                final Optional<Source> source = sourceService.getSource(article.get().getSourceId())
                if(source.isPresent()) {
                    final List<String> sortBys = source.get().getSortBysAvailable()
                    final Optional<String> sortBy = sortBys.stream()
                            .skip(ThreadLocalRandom.current().nextInt(sortBys.size()))
                            .findAny()
                    if(sortBy.isPresent()) {
                        final List<Article> articles = articleService.getArticles(source.get(), sortBy.get());
                        final Optional<Article> a = articles.stream()
                                .skip(ThreadLocalRandom.current().nextInt(articles.size()))
                                .findAny()
                        if(a.isPresent()) {
                            return [status: "ok", data: a.get()]
                        }
                    }
                }
            }
        }

        [status: "ok", message: "Favorite an article to start getting recommendations.", data: null]
    }
}
