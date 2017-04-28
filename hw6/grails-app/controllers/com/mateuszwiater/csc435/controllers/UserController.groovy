package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.domains.User
import com.mateuszwiater.csc435.services.UserService
import grails.validation.ValidationException

class UserController {
    UserService userService

    def doGet() {
        String userName = params.userName
        String password = params.password

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            final Optional<User> user = userService.getUser(userName, password)

            if(user.isPresent()) {
                login(user.get())
                render(status: 200)
            } else {
                render(status: 404, text: "User Not Found!")
            }
        } else {
            User user = session["user"] as User
            [userName: user?.userName, apiKey: user?.apiKey]
        }
    }

    def doPut() {
        final String userName = params.userName
        final String password = params.password

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            try {
                final User user = new User(userName,password)
                user.save(flush: true, failOnError: true)
                login(user)
                render(status: 200)
            } catch (ValidationException e) {
                render(status: 409, text: "User with that name already exists!")
            }
        } else {
            render(status: 400, text: "U: $userName P: $password")
        }
    }

    def doPost() {
        final String userName = params.userName
        final String password = params.password
        final String logout = params.logout

        if (logout != null && !logout.isEmpty() && logout.equalsIgnoreCase("true")) {
            this.logout()
            render(status: 200)
        } else {
            try {
                Optional<User> user = userService.getUser((session["user"] as User).getApiKey())

                if (user.isPresent()) {
                    if (userName != null && !userName.isEmpty()) {
                        user.get().setUserName(userName)
                    }
                    if (password != null && !password.isEmpty()) {
                        user.get().setPassword(password)
                    }
                    user.get().save(flush: true, failOnError: true)
                    login(user.get())
                } else {
                    this.logout()
                }
                render(status: 200)
            } catch (ValidationException e) {
                login(userService.getUser((session["user"] as User).getApiKey()).get())
                render(status: 409, "User with that name already exists!")
            }
        }
    }

    def doDelete() {
        try {
            final Optional<User> user = Optional.ofNullable(session["user"] as User)

            if(user.isPresent()) {
                user.get().delete(flush: true, failOnError: true)
                logout()
            }
            render(status: 200)
        } catch (Exception e) {
            logout()
            render(status: 500, text: "Internal Server Error")
        }
    }

    def login(final User user) {
        session["user"] = user
    }

    def logout() {
        session["user"] = null
    }
}
