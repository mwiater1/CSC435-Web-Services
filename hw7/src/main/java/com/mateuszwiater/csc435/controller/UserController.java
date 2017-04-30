package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Route;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.mateuszwiater.csc435.util.Util.render;

@SuppressWarnings("Duplicates")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static Route GET = (req, resp) -> {
        String userName = req.queryParams("userName");
        String password = req.queryParams("password");

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            try {
                final Optional<User> user = User.getUser(userName, password);

                if(user.isPresent()) {
                    login(req, user.get());
                } else {
                    resp.status(404);
                    return "User Not Found!";
                }
            } catch (SQLException e) {
                logger.error("USER GET", e);
                resp.status(500);
                logout(req);
            }
        } else {
            final Map<String, Object> model = new HashMap<>();
            final Optional<User> user = Optional.ofNullable(req.session().attribute("user"));
            model.put("currentPage", "user");
            model.put("userName", user.map(User::getUserName).orElse("Please Login"));
            model.put("apiKey", user.map(User::getApiKey).orElse("Please Login"));
            return render(model, "userView.ftl");
        }

        return "";
    };

    public static Route PUT = (req, resp) -> {
        String userName = req.queryParams("userName");
        String password = req.queryParams("password");

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            try {
                final User user = new User(userName,password);
                user.save();
                login(req, user);
            } catch (SQLException e) {
                if (e.getErrorCode() == 23505) {
                    resp.status(409);
                    return "User with that name already exists!";
                } else {
                    logger.error("USER PUT", e);
                    logout(req);
                    resp.status(500);
                    return "Internal Server Error!";
                }
            }
        }
        return "";
    };

    public static Route POST = (req, resp) -> {
        String userName = req.queryParams("userName");
        String password = req.queryParams("password");
        String logout = req.queryParams("logout");

        final String apiKey = ((User)req.session().attribute("user")).getApiKey();

        if (logout != null && !logout.isEmpty() && logout.equals("true")) {
            logout(req);
        } else if(apiKey != null) {
            try {
                final Optional<User> user = User.getUser(UUID.fromString(apiKey));

                if (user.isPresent()) {
                    if (userName != null && !userName.isEmpty()) {
                        user.get().setUserName(userName);
                    }
                    if (password != null && !password.isEmpty()) {
                        user.get().setPassword(password);
                    }

                    user.get().save();
                    login(req,user.get());
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 23505) {
                    resp.status(409);
                    return "User with that name already exists!";
                } else {
                    logger.error("USER POST", e);
                    resp.status(500);
                    logout(req);
                    return "Internal Server Error!";
                }
            }
        }
        return "";
    };

    public static Route DELETE = (req, resp) -> {
        final String apiKey = ((User)req.session().attribute("user")).getApiKey();

        if (apiKey != null) {
            try {
                final Optional<User> user = User.getUser(UUID.fromString(apiKey));

                if(user.isPresent()) {
                    user.get().delete();
                    logout(req);
                }
            } catch (SQLException e) {
                logger.error("USER DELETE", e);
                resp.status(500);
                logout(req);
                return "Internal Server Error!";
            }
        }
        return "";
    };

    private static void login(final Request req, final User user) {
        req.session().attribute("user", user);
    }

    private static void logout(final Request req) {
        req.session().removeAttribute("user");
    }
}
