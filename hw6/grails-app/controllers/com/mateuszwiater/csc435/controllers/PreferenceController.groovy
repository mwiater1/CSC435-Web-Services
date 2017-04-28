package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.Article
import com.mateuszwiater.csc435.domains.Preference
import com.mateuszwiater.csc435.domains.User
import com.mateuszwiater.csc435.services.ArticleService
import com.mateuszwiater.csc435.services.PreferenceService

class PreferenceController {
    PreferenceService preferenceService
    ArticleService articleService

    def doGet() {
        boolean favoriteParam = Boolean.valueOf(params.favorite)
        boolean readParam = Boolean.valueOf(params.read)

        // Build preference map
        Map<Long, Preference> preferenceMap = preferenceService.getPreferences((session["user"] as User).getApiKey()).collectEntries {[it.getArticleId(), it]}

        // Build the articles list
        List<Article> articles = new ArrayList<>()
        for (Preference preference : preferenceMap.values()) {
            // Only get articles which are preferred
            if(preference.isFavorite() || preference.isRead()) {
                if(favoriteParam && preference.isFavorite()) {
                    articleService.getArticle(preference.getArticleId()).ifPresent{a -> articles.add(a)}
                    continue
                }

                if(readParam && preference.isRead()) {
                    articleService.getArticle(preference.getArticleId()).ifPresent{a -> articles.add(a)}
                }
            }
        }

        [articles: articles, preferenceMap: preferenceMap, favorite: favoriteParam, read: readParam]
    }

    def doPost() {
        String favoriteParam = params.favorite
        String readParam = params.read
        String articleIdParam = params.articleId

        User u = session["user"] as User

        if(articleIdParam == null || articleIdParam.isEmpty()) {
            render(status: 400)
        } else if(favoriteParam != null || readParam != null) {
            final Preference preference = preferenceService.getPreference(u.getApiKey(),Long.valueOf(articleIdParam))
                    .orElse(new Preference(apiKey: u.getApiKey(), articleId: Long.valueOf(articleIdParam), favorite: false, read: false))

            if(favoriteParam != null) {
                preference.setFavorite(Boolean.valueOf(favoriteParam))
            }

            if(readParam != null) {
                preference.setRead(Boolean.valueOf(readParam))
            }

            preference.save(flush: true)
        }

        render(status: 200)
    }
}
