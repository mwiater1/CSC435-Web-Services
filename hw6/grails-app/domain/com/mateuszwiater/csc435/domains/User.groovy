package com.mateuszwiater.csc435.domains

class User {
    String userName, password

    UUID apiKey

    User(String userName, String password) {
        this(userName: userName, password: password)
        apiKey = UUID.randomUUID()
    }

    static constraints = {
        userName unique: true
    }
}
