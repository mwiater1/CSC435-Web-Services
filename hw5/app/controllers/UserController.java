package controllers;

import actions.Auth;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.UserView;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("Duplicates")
public class UserController extends Controller {

    public Result doGet() {
        String userName = request().getQueryString("userName");
        String password = request().getQueryString("password");

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            final Optional<User> user = User.getUser(userName, password);

            if(user.isPresent()) {
                login(user.get());
                return ok();
            } else {
                return notFound("User Not Found!");
            }
        } else {
            return ok(UserView.render(session("userName"),session("apiKey")));
        }
    }

    public Result doPut() {
        final String userName = request().body().asFormUrlEncoded().getOrDefault("userName", new String[]{null})[0];
        final String password = request().body().asFormUrlEncoded().getOrDefault("password", new String[]{null})[0];

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            try {
                final User user = new User(userName,password);
                user.save();
                login(user);
                return ok();
            } catch (PersistenceException e) {
                return status(409, "User with that name already exists!");
            }
        } else {
            return status(400);
        }
    }

    @With(Auth.class)
    public Result doPost() {
        final String userName = request().body().asFormUrlEncoded().getOrDefault("userName", new String[]{null})[0];
        final String password = request().body().asFormUrlEncoded().getOrDefault("password", new String[]{null})[0];
        final String logout = request().body().asFormUrlEncoded().getOrDefault("logout", new String[]{null})[0];

        if (logout != null && !logout.isEmpty() && logout.equals("true")) {
            logout();
            return ok();
        } else {
            Optional<User> oldUser = null;
            try {
                oldUser = User.getUser(UUID.fromString(session("apiKey")));

                if (oldUser.isPresent()) {
                    final User user = new User(oldUser.get().getUserName(), oldUser.get().getPassword(), oldUser.get().getApiKey());
                    if (userName != null && !userName.isEmpty()) {
                        user.setUserName(userName);
                    }
                    if (password != null && !password.isEmpty()) {
                        user.setPassword(password);
                    }

                    oldUser.get().delete();
                    user.save();
                    login(user);
                } else {
                    logout();
                }
                return ok();
            } catch (PersistenceException e) {
                new User(oldUser.get().getUserName(), oldUser.get().getPassword(), oldUser.get().getApiKey()).save();
                return status(409, "User with that name already exists!");
            }
        }
    }

    @With(Auth.class)
    public Result doDelete() throws ServletException, IOException {
        try {
            final Optional<User> user = User.getUser(UUID.fromString(session("apiKey")));

            if(user.isPresent()) {
                user.get().delete();
                logout();
            }
            return ok();
        } catch (PersistenceException e) {
            logout();
            return status(500, "Internal Server Error");
        }
    }

    private void login(final User user) {
        session("apiKey", user.getApiKey().toString());
        session("userName", user.getUserName());
    }

    private void logout() {
        session().remove("apiKey");
        session().remove("userName");
    }
}
