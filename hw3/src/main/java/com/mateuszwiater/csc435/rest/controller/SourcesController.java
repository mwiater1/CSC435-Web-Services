package com.mateuszwiater.csc435.rest.controller;

import com.mateuszwiater.csc435.model.Category;
import com.mateuszwiater.csc435.model.Country;
import com.mateuszwiater.csc435.model.Language;
import com.mateuszwiater.csc435.model.Source;
import com.mateuszwiater.csc435.rest.util.Auth;
import com.mateuszwiater.csc435.rest.view.SourcesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static com.mateuszwiater.csc435.rest.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.rest.util.Response.Status.OK;

public class SourcesController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(com.mateuszwiater.csc435.controller.SourcesController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String apiKey = req.getParameter("apiKey");
        String countryParam = req.getParameter("country");
        String languageParam = req.getParameter("language");
        String categoryParam = req.getParameter("category");
        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try {
                Auth.AuthResponse authResponse = Auth.authenticate(apiKey);
                if (authResponse.isAuthenticated()) {
                    Optional<Country> country = Country.getCountry(countryParam);
                    Optional<Language> language = Language.getLanguage(languageParam);
                    Optional<Category> category = Category.getCategory(categoryParam);

                    resp.setStatus(400);
                    if(categoryParam != null && !category.isPresent()) {
                        out.print(SourcesView.getView(FAIL, "Invalid Category '" + categoryParam + "'", new ArrayList<>()));
                    }else if(countryParam != null && !country.isPresent()) {
                        out.print(SourcesView.getView(FAIL, "Invalid Country Code '" + countryParam + "'", new ArrayList<>()));
                    } else if(languageParam != null && !language.isPresent()) {
                        out.print(SourcesView.getView(FAIL, "Invalid Language Code '" + languageParam + "'", new ArrayList<>()));
                    } else {
                        resp.setStatus(200);
                        out.print(SourcesView.getView(OK, "", Source.getSources(category.orElse(null), language.orElse(null), country.orElse(null))));
                    }
                } else {
                    resp.setStatus(400);
                    out.print(authResponse.getView());
                }
            } catch (SQLException e) {
                logger.error("SOURCES GET", e);
                resp.setStatus(500);
                out.print(SourcesView.getView(FAIL, "Internal Server Error", new ArrayList<>()));
            }
        } catch (IOException e) {
            logger.error("SOURCES GET", e);
        }
    }
}
