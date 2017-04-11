package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.PreferenceView;

public class PreferenceController extends Controller {

    public Result doGet() {
        return ok(PreferenceView.render());
    }
}
