package controllers;

import actions.RestfulAuth;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.Status;
import views.html.JsonUserView;

import javax.persistence.PersistenceException;
import java.util.*;


@SuppressWarnings("Duplicates")
public class RestfulUserController extends Controller {

    public Result doGet() {
        String userName = request().getQueryString("userName");
        String password = request().getQueryString("password");

        Result result;

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            final Optional<User> user = User.getUser(userName, password);

            if(user.isPresent()) {
                result = ok(JsonUserView.render(Status.OK, "", user.get()));
            } else {
                result = status(404, JsonUserView.render(Status.FAIL, "User Not Found!", null));
            }
        } else {
            result = status(400, JsonUserView.render(Status.FAIL, "Empty or Missing Parameters.", null));
        }

        return result.as("application/json; charset=UTF-8");
    }

    @With(RestfulAuth.class)
    public Result doPost() {
        String userName = request().getQueryString("userName");
        String password = request().getQueryString("password");
        String apiKey = request().getQueryString("apiKey");

        Result result;
        User oldUser = null;

        try {
            oldUser = User.getUser(UUID.fromString(apiKey)).get();
            final User user = new User(oldUser.getUserName(), oldUser.getPassword(), oldUser.getApiKey());
            if (userName != null && !userName.isEmpty()) {
                user.setUserName(userName);
            }
            if (password != null && !password.isEmpty()) {
                user.setPassword(password);
            }

            oldUser.delete();
            user.save();
            result = ok(JsonUserView.render(Status.OK, "", user));
        } catch (PersistenceException e) {
            new User(oldUser.getUserName(), oldUser.getPassword(), oldUser.getApiKey()).save();
            result = status(409, JsonUserView.render(Status.FAIL, "User With That Name Already Exists!", null));
        }

        return result.as("application/json; charset=UTF-8");
    }

    public Result doPut() {
        String userName = request().getQueryString("userName");
        String password = request().getQueryString("password");

        Result result;

        try {
            if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
                final User user = new User(userName, password);
                user.save();
                result = ok(JsonUserView.render(Status.OK, "", user));
            } else {
                result = status(400, JsonUserView.render(Status.FAIL, "Empty or Missing Parameters.", null));
            }
        } catch (PersistenceException e) {
            result = status(409, JsonUserView.render(Status.FAIL, "User With That Name Already Exists!", null));
        }

        return result.as("application/json; charset=UTF-8");
    }

    @With(RestfulAuth.class)
    public Result doDelete() {
        String apiKey = request().getQueryString("apiKey");

        User.getUser(UUID.fromString(apiKey)).get().delete();
        return ok(JsonUserView.render(Status.OK, "", null)).as("application/json; charset=UTF-8");
    }
}
