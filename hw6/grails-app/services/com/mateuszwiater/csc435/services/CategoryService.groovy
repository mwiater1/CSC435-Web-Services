package com.mateuszwiater.csc435.services

import com.mateuszwiater.csc435.domains.Source
import com.mateuszwiater.csc435.domains.Category
import grails.transaction.Transactional

@Transactional
class CategoryService {

    List<Category> getCategories() {
        return Source.where {}
                .projections {distinct 'category'}
                .list().stream()
                .map{s -> new Category(name: s)}
                .collect()
    }

    def getCategory(String category) {
        return Optional.ofNullable(Source.findByCategory(category)).map({s -> new Category(name: s.getCategory())})
    }
}
