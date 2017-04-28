package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.Country
import com.mateuszwiater.csc435.domains.Language
import com.mateuszwiater.csc435.domains.Category
import com.mateuszwiater.csc435.services.CategoryService
import com.mateuszwiater.csc435.services.CountryService
import com.mateuszwiater.csc435.services.LanguageService
import com.mateuszwiater.csc435.services.SourceService

class RestfulSourceController {
    SourceService sourceService
    CountryService countryService
    CategoryService categoryService
    LanguageService languageService

    def doGet() {
        String countryParam = params.country
        String languageParam = params.language
        String categoryParam = params.category

        Optional<Country> country = countryService.getCountry(countryParam)
        Optional<Language> language = languageService.getLanguage(languageParam)
        Optional<Category> category = categoryService.getCategory(categoryParam)

        response.status = 400

        if(categoryParam != null && !category.isPresent()) {
            [status: "fail", message: "Invalid Category '$categoryParam'", data: new ArrayList<>()]
        }else if(countryParam != null && !country.isPresent()) {
            [status: "fail", message: "Invalid Country Code '$countryParam'", data: new ArrayList<>()]
        } else if(languageParam != null && !language.isPresent()) {
            [status: "fail", message: "Invalid Language Code '$languageParam'", data: new ArrayList<>()]
        } else {
            response.status = 200
            [status: "ok", message: "", data: sourceService.getSources(category.orElse(null), language.orElse(null), country.orElse(null))]
        }
    }
}
