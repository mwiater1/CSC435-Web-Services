package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.SourcesView;

public class SourcesController extends Controller {

    public Result doGet() {
        return ok(SourcesView.render());
    }
}
