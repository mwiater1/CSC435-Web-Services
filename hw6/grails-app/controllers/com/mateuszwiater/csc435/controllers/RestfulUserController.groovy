package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.User
import com.mateuszwiater.csc435.services.UserService
import grails.validation.ValidationException
import org.hibernate.NonUniqueObjectException
import org.springframework.dao.DuplicateKeyException

class RestfulUserController {
    UserService userService

    def doGet() {
        String userName = params.userName
        String password = params.password

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            final Optional<User> user = userService.getUser(userName, password)

            if(user.isPresent()) {
                login(user.get())
                [status: "ok", user: user.get()]
            } else {
                response.status = 404
                [status: "fail", message: "User Not Found!"]
            }
        } else {
            response.status = 400
            [status: "fail", message: "Empty or Missing Parameters"]
        }
    }

    def doPost() {
        String userName = params.userName
        String password = params.password
        String logout = params.logout
        User user = session["user"] as User

        if(logout != null && Boolean.valueOf(logout)) {
            this.logout()
            [status: "ok", message: "Logged Out"]
        } else {
            try {
                if (userName != null && !userName.isEmpty()) {
                    user.setUserName(userName)
                }
                if (password != null && !password.isEmpty()) {
                    user.setPassword(password)
                }

                user.save(flush: true, failOnError: true)
                [status: "ok", user: user]
            } catch (ValidationException | DuplicateKeyException e) {
                login(userService.getUser(user.getApiKey()).get())
                [status: "fail", message: "User With That Name Already Exists!"]
            }
        }
    }

    def doPut() {
        String userName = params.userName
        String password = params.password

        try {
            if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
                final User user = new User(userName, password)
                user.save(flush: true, failOnError: true)
                login(user)
                [status: "ok", user: user]
            } else {
                response.status = 400
                [status: "fail", message: "Empty or Missing Parameters."]
            }
        } catch (ValidationException e) {
            response.status = 409
            [status: "fail", message: "User With That Name Already Exists!"]
        }
    }

    def doDelete() {
        User user = session["user"] as User

        user.delete()
        logout()
        [status: "ok"]
    }

    def login(final User user) {
        session["user"] = user
    }

    def logout() {
        session["user"] = null
    }
}
