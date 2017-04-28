package com.mateuszwiater.csc435.controllers

import com.mateuszwiater.csc435.services.CategoryService

class RestfulCategoryController {
    CategoryService categoryService

    def doGet() {
        [status: "ok", message: "", data: categoryService.getCategories().collect {it.name}]
    }
}
