package com.mateuszwiater.csc435.domains

class Language {
    String name, code

    Language(String code) {
        this.code = code
        this.name = new Locale(code).getDisplayLanguage()
    }

    static constraints = {

    }
}
