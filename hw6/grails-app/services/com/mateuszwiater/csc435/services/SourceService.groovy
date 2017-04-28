package com.mateuszwiater.csc435.services

import com.mateuszwiater.csc435.domains.Category
import com.mateuszwiater.csc435.domains.Country
import com.mateuszwiater.csc435.domains.Language
import com.mateuszwiater.csc435.domains.Source
import grails.gorm.DetachedCriteria
import grails.transaction.Transactional

@Transactional
class SourceService {

    def getSource(String id) {
        return Optional.ofNullable(Source.findById(id))
    }

    def getSources(Category category, Language language, Country country) {
        final DetachedCriteria query = Source.where {}

        Optional.ofNullable(category).ifPresent{c -> query.eq("category", c.getName())}
        Optional.ofNullable(language).ifPresent{l -> query.eq("language", l.getCode())}
        Optional.ofNullable(country).ifPresent{c -> query.eq("country", c.getCode())}

        return query.list()
    }
}
