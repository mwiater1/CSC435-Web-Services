package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.Article
import com.mateuszwiater.csc435.domains.Preference
import com.mateuszwiater.csc435.domains.User
import com.mateuszwiater.csc435.services.ArticleService
import com.mateuszwiater.csc435.services.PreferenceService

class RestfulPreferenceController {
    ArticleService articleService
    PreferenceService preferenceService

    def doGet() {
        final boolean read = params.boolean("read")
        final boolean favorite = params.boolean("favorite")
        final User user = session["user"] as User

        final List<Preference> preferences = preferenceService.getPreferences(user.getApiKey()).stream()
                .filter{p -> (p.isFavorite() && favorite) || (p.isRead() && read)}
                .collect()

        final List<Article> articles = new ArrayList<>()

        for (Preference preference : preferences) {
            articleService.getArticle(preference.getArticleId()).ifPresent({a -> articles.add(a)})
        }

        [status: "ok", data: articles]
    }

    def doPost() {
        final String readParam = params.read
        final String favoriteParam = params.favorite
        final String articleIdParam = params.articleId
        final User user = session["user"] as User

        Optional<Article> article

        try {
            article = articleService.getArticle(Long.valueOf(articleIdParam))
        } catch (NumberFormatException e) {
            article = Optional.empty()
        }

        if(article.isPresent()) {
            if(favoriteParam != null || readParam != null) {
                final Preference preference = preferenceService.getPreference(user.getApiKey(), Long.valueOf(articleIdParam))
                        .orElse(new Preference(apiKey: user.getApiKey(), articleId: Long.valueOf(articleIdParam), favorite: false, read: false))

                if (favoriteParam != null) {
                    preference.setFavorite(Boolean.valueOf(favoriteParam))
                }

                if (readParam != null) {
                    preference.setRead(Boolean.valueOf(readParam))
                }

                preference.save(flush: true)
            }
            [status: "ok", data: new ArrayList<>()]
        } else {
            response.status = 400
            [status: "fail", message: "Invalid Article ID: '$articleIdParam'", data: new ArrayList<>()]
        }
    }
}
