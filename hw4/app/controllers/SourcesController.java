package controllers;

import models.Category;
import models.Country;
import models.Language;
import models.Source;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.SourcesView;

public class SourcesController extends Controller {

    public Result doGet() {
        return ok(SourcesView.render(Source.getSources(), Category.getCategories(), Language.getLanguages(), Country.getCountries(),
                "", "en", ""));
    }
}
