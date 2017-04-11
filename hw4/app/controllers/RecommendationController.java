package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.RecommendationView;

public class RecommendationController extends Controller {

    public Result doGet() {
        return ok(RecommendationView.render());
    }
}
