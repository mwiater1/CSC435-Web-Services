package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.Country;
import com.mateuszwiater.csc435.view.CountryView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.mateuszwiater.csc435.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.util.Response.Status.OK;

public class RestfulCountryController {
    private static Logger logger = LoggerFactory.getLogger(RestfulCountryController.class);

    public static Route GET = (req, resp) -> {
        try {
            return CountryView.getView(OK,"", Country.getCountries());
        } catch (SQLException e) {
            logger.error("COUNTRY GET", e);
            resp.status(500);
            return CountryView.getView(FAIL, "Internal Server Error", new ArrayList<>());
        }
    };
}
