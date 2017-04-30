package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.Language;
import com.mateuszwiater.csc435.view.LanguageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.mateuszwiater.csc435.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.util.Response.Status.OK;

public class RestfulLanguageController {
    private static Logger logger = LoggerFactory.getLogger(RestfulLanguageController.class);

    public static Route GET = (req, resp) -> {
        try {
            return LanguageView.getView(OK,"", Language.getLanguages());
        } catch (SQLException e) {
            logger.error("LANGUAGE GET", e);
            resp.status(500);
            return LanguageView.getView(FAIL, "Internal Server Error", new ArrayList<>());
        }
    };
}
