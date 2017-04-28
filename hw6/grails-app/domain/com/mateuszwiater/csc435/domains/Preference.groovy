package com.mateuszwiater.csc435.domains

class Preference {
    UUID apiKey
    long articleId
    boolean favorite, read

    static constraints = {
    }
}
