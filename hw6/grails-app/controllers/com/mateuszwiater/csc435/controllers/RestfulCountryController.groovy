package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.services.CountryService

class RestfulCountryController {
    CountryService countryService

    def doGet() {
        [status: "ok", data: countryService.getCountries()]
    }
}
