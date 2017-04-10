package com.mateuszwiater.csc435.controller;

import com.google.common.collect.Lists;
import com.mateuszwiater.csc435.model.Category;
import com.mateuszwiater.csc435.model.Country;
import com.mateuszwiater.csc435.model.Language;
import com.mateuszwiater.csc435.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@SuppressWarnings("Duplicates")
public class SourcesController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(SourcesController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = formatParameter(req.getParameter("category"));
        String language = formatParameter(req.getParameter("language"));
        String country = formatParameter(req.getParameter("country"));
        String apiKey = (String) req.getSession().getAttribute("apiKey");

        if(apiKey == null) {
            resp.sendRedirect("/hw3");
        } else {
            req.getSession().setAttribute("selected_category", category);
            req.getSession().setAttribute("selected_language", language);
            req.getSession().setAttribute("selected_country", country);

            try {
                req.getSession().setAttribute("categories", Category.getCategories());
                req.getSession().setAttribute("languages", Language.getLanguages());
                req.getSession().setAttribute("countries", Country.getCountries());

                Optional<Category> cat = Category.getCategory(category);
                Optional<Language> lang = Language.getLanguage(language);
                Optional<Country> cou = Country.getCountry(country);

                req.getSession().setAttribute("sources", Lists.partition(Source.getSources(cat.orElse(null), lang.orElse(null), cou.orElse(null)), 3));
                req.getRequestDispatcher("/view/SourcesView.jsp").forward(req, resp);
            } catch (SQLException e) {
                logger.error("SOURCES GET", e);
                req.getSession().removeAttribute("apiKey");
                resp.sendRedirect("/hw3");
            }
        }
    }

    private String formatParameter(final String param) {
         return param == null || param.isEmpty() ? "any" : param;
    }
}
