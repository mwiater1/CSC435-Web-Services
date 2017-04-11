package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.UserView;

public class UserController extends Controller {

    public Result doGet() {
        return ok(UserView.render());
    }
}
