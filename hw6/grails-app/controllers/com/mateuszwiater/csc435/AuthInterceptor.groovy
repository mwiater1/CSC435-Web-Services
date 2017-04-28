package com.mateuszwiater.csc435


class AuthInterceptor {

    AuthInterceptor() {
        matchAll()
                .excludes(controller: "user", action: "doGet")
                .excludes(controller: "user", action: "doPut")
                .excludes(controller: "restfulUser", action: "doGet")
                .excludes(controller: "restfulUser", action: "doPut")
    }

    boolean before() {
        if(session["user"] == null) {
            if((params.controller?:"").contains("restful")) {
                response.status = 400
                render(view: "/auth", model: [status: "fail", message: "User Not Authenticated!"])
            } else {
                redirect(uri: "hw6")
            }
            false
        } else {
            true
        }
    }

    boolean after() {
        true
    }
}
