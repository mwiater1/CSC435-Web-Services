package com.mateuszwiater.csc435.domains

class Article {
    String url, title, sortBy, author, sourceId, urlToImage, publishedAt, description

    static constraints = {
        url maxSize: 1000
        author maxSize: 1000
        urlToImage maxSize: 1000
        description maxSize: 1000
    }
}
