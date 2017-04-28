package com.mateuszwiater.csc435.controllers

class UrlMappings {

    static mappings = {
        group "/hw6", {
            "/"(method: "get", controller: "user", action: "doGet")
            "/"(method: "put", controller: "user", action: "doPut")
            "/"(method: "post", controller: "user", action: "doPost")
            "/"(method: "delete", controller: "user", action: "doDelete")

            "/sources"(method: "get", controller: "source", action: "doGet")
            "/articles"(method: "get", controller: "article", action: "doGet")
            "/recommendation"(method: "get", controller: "recommendation", action: "doGet")

            group "/preference", {
                "/"(method: "get", controller: "preference", action: "doGet")
                "/"(method: "post", controller: "preference", action: "doPost")
            }

            group "/restful", {
                "/language"(method: "get", controller: "restfulLanguage", action: "doGet")
                "/country"(method: "get", controller: "restfulCountry", action: "doGet")
                "/category"(method: "get", controller: "restfulCategory", action: "doGet")
                "/sources"(method: "get", controller: "restfulSource", action: "doGet")
                "/recommend"(method: "get", controller: "restfulRecommend", action: "doGet")
                "/articles"(method: "get", controller: "restfulArticle", action: "doGet")

                group "/preference", {
                    "/"(method: "get", controller: "restfulPreference", action: "doGet")
                    "/"(method: "post", controller: "restfulPreference", action: "doPost")
                }

                group "/user", {
                    "/"(method: "get", controller: "restfulUser", action: "doGet")
                    "/"(method: "post", controller: "restfulUser", action: "doPost")
                    "/"(method: "put", controller: "restfulUser", action: "doPut")
                    "/"(method: "delete", controller: "restfulUser", action: "doDelete")
                }
            }
        }
    }
}
