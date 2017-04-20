package controllers;

import actions.RestfulAuth;
import models.Category;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.Status;
import views.html.JsonCategoryView;

import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@With(RestfulAuth.class)
public class RestfulCategoryController extends Controller {

    public Result doGet() {
        return ok(JsonCategoryView.render(Status.OK, "", Category.getCategories()
                .stream()
                .map(c -> c.getName())
                .collect(Collectors.toList()))).as("application/json; charset=UTF-8");
    }
}
