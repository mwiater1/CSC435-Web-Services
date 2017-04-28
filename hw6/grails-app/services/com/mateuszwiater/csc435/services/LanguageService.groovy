package com.mateuszwiater.csc435.services

import com.mateuszwiater.csc435.domains.Language
import com.mateuszwiater.csc435.domains.Source
import grails.transaction.Transactional

@Transactional
class LanguageService {

    def getLanguage(String language) {
        return Optional.ofNullable(Source.findByLanguage(language))
                .map({s -> new Language(s.getLanguage())})
    }

    def getLanguages() {
        return Source.where {}
                .projections {distinct 'language'}
                .list().stream()
                .map{s -> new Language(s as String)}
                .collect()
    }
}
