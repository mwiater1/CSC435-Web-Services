package controllers;

import actions.RestfulAuth;
import models.Country;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.Status;
import views.html.JsonCountryView;

@SuppressWarnings("Duplicates")
@With(RestfulAuth.class)
public class RestfulCountryController extends Controller {

     public Result doGet() {
        return ok(JsonCountryView.render(Status.OK, "", Country.getCountries())).as("application/json; charset=UTF-8");
    }
}
