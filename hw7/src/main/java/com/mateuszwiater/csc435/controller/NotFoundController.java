package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.util.Response;
import com.mateuszwiater.csc435.view.NotFoundView;
import spark.Route;

public class NotFoundController {

    public static Route REST = (req, resp) -> {
        resp.status(404);
        return NotFoundView.getView(Response.Status.FAIL, "Endpoint Not Found!");
    };

    public static Route UI = (req, resp) -> {
        resp.redirect("/hw7");
        return "";
    };
}
