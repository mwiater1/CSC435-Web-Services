package com.mateuszwiater.csc435.domains

class Source {
    List<String> sortBysAvailable

    String id, url, name, category, language, country, description

    static constraints = {
        id generator: 'assigned'
        url maxSize: 1000
        description maxSize: 1000
    }
}
