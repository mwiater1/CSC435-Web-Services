package com.mateuszwiater.csc435.rest.controller;

import com.mateuszwiater.csc435.model.Country;
import com.mateuszwiater.csc435.rest.util.Auth;
import com.mateuszwiater.csc435.rest.view.CountryView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.mateuszwiater.csc435.rest.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.rest.util.Response.Status.OK;

public class CountryController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String apiKey = req.getParameter("apiKey");
        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try {
                Auth.AuthResponse authResponse = Auth.authenticate(apiKey);
                if (authResponse.isAuthenticated()) {
                    out.print(CountryView.getView(OK,"", Country.getCountries()));
                } else {
                    resp.setStatus(400);
                    out.print(authResponse.getView());
                }
            } catch (SQLException e) {
                logger.error("COUNTRY GET", e);
                resp.setStatus(500);
                out.print(CountryView.getView(FAIL, "Internal Server Error", new ArrayList<>()));
            }
        } catch (IOException e) {
            logger.error("COUNTRY GET", e);
        }
    }
}
