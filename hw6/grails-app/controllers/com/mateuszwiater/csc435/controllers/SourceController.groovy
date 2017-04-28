package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.Country
import com.mateuszwiater.csc435.domains.Language
import com.mateuszwiater.csc435.domains.Category
import com.mateuszwiater.csc435.services.CategoryService
import com.mateuszwiater.csc435.services.CountryService
import com.mateuszwiater.csc435.services.LanguageService
import com.mateuszwiater.csc435.services.SourceService

import static com.mateuszwiater.csc435.Util.*

class SourceController {
    SourceService sourceService
    CountryService countryService
    CategoryService categoryService
    LanguageService languageService

    def doGet() {
        String category = orElse(params.category, "any")
        String language = orElse(params.language,"any")
        String country = orElse(params.country,"any")

        Optional<Category> cat = categoryService.getCategory(category)
        Optional<Language> lang = languageService.getLanguage(language)
        Optional<Country> cou = countryService.getCountry(country)

        [sources: sourceService.getSources(cat.orElse(null), lang.orElse(null), cou.orElse(null)),
         categories: categoryService.getCategories(), languages: languageService.getLanguages(),
         countries: countryService.getCountries(), selectedCategory: category, selectedLanguage: language,
         selectedCountry: country]
    }
}
