package com.mateuszwiater.csc435.services

import com.mateuszwiater.csc435.domains.User
import grails.transaction.Transactional

@Transactional
class UserService {

    def getUser(UUID apiKey) {
        return Optional.ofNullable(User.findByApiKey(apiKey))
    }

    def getUser(String userName, String password) {
        return Optional.ofNullable(User.findByUserNameAndPassword(userName, password))
    }
}
