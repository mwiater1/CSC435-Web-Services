package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.services.LanguageService

class RestfulLanguageController {
    LanguageService languageService

    def doGet() {
        [status: "ok", data: languageService.getLanguages()]
    }
}
