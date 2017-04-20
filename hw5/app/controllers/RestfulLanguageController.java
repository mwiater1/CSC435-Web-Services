package controllers;

import actions.RestfulAuth;
import models.Language;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.Status;
import views.html.JsonLanguageView;

@SuppressWarnings("Duplicates")
@With(RestfulAuth.class)
public class RestfulLanguageController extends Controller {

    public Result doGet() {
        return ok(JsonLanguageView.render(Status.OK, "", Language.getLanguages())).as("application/json; charset=UTF-8");
    }
}
