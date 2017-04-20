package controllers;

import actions.RestfulAuth;
import models.Category;
import models.Country;
import models.Language;
import models.Source;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.Status;
import views.html.JsonSourceView;

import java.util.ArrayList;
import java.util.Optional;

@SuppressWarnings("Duplicates")
@With(RestfulAuth.class)
public class RestfulSourcesController extends Controller {

    public Result doGet() {
        String countryParam = request().getQueryString("country");
        String languageParam = request().getQueryString("language");
        String categoryParam = request().getQueryString("category");

        Optional<Country> country = Country.getCountry(countryParam);
        Optional<Language> language = Language.getLanguage(languageParam);
        Optional<Category> category = Category.getCategory(categoryParam);

        if(categoryParam != null && !category.isPresent()) {
            return status(400, JsonSourceView.render(Status.FAIL, "Invalid Category '" + categoryParam + "'", new ArrayList<>()))
                    .as("application/json; charset=UTF-8");
        }else if(countryParam != null && !country.isPresent()) {
            return status(400, JsonSourceView.render(Status.FAIL, "Invalid Country Code '" + countryParam + "'", new ArrayList<>()))
                    .as("application/json; charset=UTF-8");
        } else if(languageParam != null && !language.isPresent()) {
            return status(400, JsonSourceView.render(Status.FAIL, "Invalid Language Code '" + languageParam + "'", new ArrayList<>()))
                    .as("application/json; charset=UTF-8");
        } else {
            return ok(JsonSourceView.render(Status.OK, "", Source.getSources(category.orElse(null), language.orElse(null), country.orElse(null))))
                    .as("application/json; charset=UTF-8");
        }
    }
}
