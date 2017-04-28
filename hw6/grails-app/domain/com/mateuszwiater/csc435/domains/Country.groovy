package com.mateuszwiater.csc435.domains

class Country {
    String name, code

    Country(String code) {
        this.code = code
        this.name = new Locale("", code).getDisplayCountry()
    }

    static constraints = {
    }
}
