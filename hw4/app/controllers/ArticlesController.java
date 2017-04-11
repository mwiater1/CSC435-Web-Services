package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.ArticlesView;

public class ArticlesController extends Controller {

    public Result doGet() {
        return ok(ArticlesView.render());
    }
}
