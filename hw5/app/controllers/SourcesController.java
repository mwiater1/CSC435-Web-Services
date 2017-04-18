package controllers;

import actions.Auth;
import com.google.common.collect.Lists;
import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.SourcesView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@With(Auth.class)
@SuppressWarnings("Duplicates")
public class SourcesController extends Controller {

    public Result doGet() {
        String category = request().getQueryString("category");
        String language = request().getQueryString("language");
        String country = request().getQueryString("country");
        User user = User.getUser(UUID.fromString(session("apiKey"))).get();

        Optional<Category> cat = Category.getCategory(category);
        Optional<Language> lang = Language.getLanguage(language);
        Optional<Country> cou = Country.getCountry(country);

        return ok(SourcesView.render(Source.getSources(cat.orElse(null), lang.orElse(null), cou.orElse(null)),
                Category.getCategories(), Language.getLanguages(), Country.getCountries(), category, language, country));
    }
}
