package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.Article
import com.mateuszwiater.csc435.domains.Preference
import com.mateuszwiater.csc435.domains.Source
import com.mateuszwiater.csc435.domains.User
import com.mateuszwiater.csc435.services.ArticleService
import com.mateuszwiater.csc435.services.PreferenceService
import com.mateuszwiater.csc435.services.SourceService

import java.util.concurrent.ThreadLocalRandom

class RecommendationController {
    ArticleService articleService
    SourceService sourceService
    PreferenceService preferenceService

    def doGet() {
        Map<Long, Preference> preferenceMap = preferenceService.getPreferences((session["user"] as User).getApiKey())
                .collectEntries {[it.getArticleId(), it]}

        List<Preference> favorites = preferenceMap.values()
                .stream().filter{p -> p.isFavorite()}.collect()

        final List<Article> articles = new ArrayList<>()

        if(favorites.size() != 0) {
            Preference p = favorites.get(ThreadLocalRandom.current().nextInt(favorites.size()))
            Optional<Article> a = articleService.getArticle(p.getArticleId())
            if(a.isPresent()) {
                Optional<Source> s = sourceService.getSource(a.get().getSourceId())
                if(s.isPresent()) {
                    final List<String> sortBysAvailable = s.get().getSortBysAvailable()
                    final String sortBy = sortBysAvailable.get(ThreadLocalRandom.current().nextInt(sortBysAvailable.size()))
                    List<Article> al = articleService.getArticles(s.get(), sortBy).stream()
                            .filter{ar -> !Objects.equals(ar.getId(), p.getArticleId())}
                            .collect()

                    articles.add(al.get(ThreadLocalRandom.current().nextInt(al.size())))
                }
            }
        }

        [articles: articles, preferenceMap: preferenceMap]
    }
}
