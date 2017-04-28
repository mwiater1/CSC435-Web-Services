package com.mateuszwiater.csc435.services

import com.mateuszwiater.csc435.domains.Article
import com.mateuszwiater.csc435.domains.Source
import grails.transaction.Transactional

@Transactional
class ArticleService {

    def getArticle(long id) {
        return Optional.ofNullable(Article.get(id))
    }

    def getArticles(Source source, String sortBy) {
        return Article.findAllBySourceIdAndSortBy(source.getId(), sortBy)
    }
}
