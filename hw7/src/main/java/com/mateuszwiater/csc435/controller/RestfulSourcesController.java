package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.*;
import com.mateuszwiater.csc435.view.SourcesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static com.mateuszwiater.csc435.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.util.Response.Status.OK;

public class RestfulSourcesController {
    private static Logger logger = LoggerFactory.getLogger(com.mateuszwiater.csc435.controller.SourcesController.class);

    public static Route GET = (req, resp) -> {
        String countryParam = req.queryParams("country");
        String languageParam = req.queryParams("language");
        String categoryParam = req.queryParams("category");

        try {
            Optional<Country> country = Country.getCountry(countryParam);
            Optional<Language> language = Language.getLanguage(languageParam);
            Optional<Category> category = Category.getCategory(categoryParam);

            resp.status(400);
            if(categoryParam != null && !category.isPresent()) {
                return SourcesView.getView(FAIL, "Invalid Category '" + categoryParam + "'", new ArrayList<>());
            }else if(countryParam != null && !country.isPresent()) {
                return SourcesView.getView(FAIL, "Invalid Country Code '" + countryParam + "'", new ArrayList<>());
            } else if(languageParam != null && !language.isPresent()) {
                return SourcesView.getView(FAIL, "Invalid Language Code '" + languageParam + "'", new ArrayList<>());
            } else {
                resp.status(200);
                return SourcesView.getView(OK, "", Source.getSources(category.orElse(null), language.orElse(null), country.orElse(null)));
            }
        } catch (SQLException e) {
            logger.error("SOURCES GET", e);
            resp.status(500);
            return SourcesView.getView(FAIL, "Internal Server Error", new ArrayList<>());
        }
    };
}
