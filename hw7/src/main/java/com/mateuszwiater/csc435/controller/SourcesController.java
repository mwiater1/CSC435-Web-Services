package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.Category;
import com.mateuszwiater.csc435.model.Country;
import com.mateuszwiater.csc435.model.Language;
import com.mateuszwiater.csc435.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.mateuszwiater.csc435.util.Util.render;

@SuppressWarnings("Duplicates")
public class SourcesController {
    private static Logger logger = LoggerFactory.getLogger(SourcesController.class);

    public static Route GET = (req, resp) -> {
        String category = formatParameter(req.queryParams("category"));
        String language = formatParameter(req.queryParams("language"));
        String country = formatParameter(req.queryParams("country"));

        final Map<String, Object> model = new HashMap<>();
        model.put("currentPage", "sources");
        model.put("selectedCategory", category);
        model.put("selectedLanguage", language);
        model.put("selectedCountry", country);

        try {
            model.put("categories", Category.getCategories());
            model.put("languages", Language.getLanguages());
            model.put("countries", Country.getCountries());

            Optional<Category> cat = Category.getCategory(category);
            Optional<Language> lang = Language.getLanguage(language);
            Optional<Country> cou = Country.getCountry(country);

            model.put("sources", Source.getSources(cat.orElse(null), lang.orElse(null), cou.orElse(null)));

            return render(model, "sourceView.ftl");
        } catch (SQLException e) {
            logger.error("SOURCES GET", e);
            resp.status(500);
            return "Internal Server Error.";
        }
    };

    private static String formatParameter(final String param) {
         return param == null || param.isEmpty() ? "any" : param;
    }
}
