package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.User;
import com.mateuszwiater.csc435.view.UserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Route;

import java.sql.SQLException;
import java.util.*;

import static com.mateuszwiater.csc435.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.util.Response.Status.OK;

@SuppressWarnings("Duplicates")
public class RestfulUserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static Route GET = (req, resp) -> {
        String userName = req.queryParams("userName");
        String password = req.queryParams("password");

        try {
            if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
                final Optional<User> user = User.getUser(userName, password);

                if(user.isPresent()) {
                    login(req, user.get());
                    return UserView.getView(OK, "", user.get());
                } else {
                    resp.status(404);
                    return UserView.getView(FAIL, "User Not Found!", null);
                }
            } else {
                resp.status(400);
                return UserView.getView(FAIL, "Empty or Missing Parameters.", null);
            }
        } catch (SQLException e) {
            logger.error("USER GET", e);
            resp.status(500);
            return UserView.getView(FAIL, "Internal Server Error", null);
        }
    };

    public static Route PUT = (req, resp) -> {
        final String userName = req.queryParams("userName");
        final String password = req.queryParams("password");

        try {
            if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
                final User user = new User(userName, password);
                user.save();
                login(req, user);
                return UserView.putView(OK, "", user);
            } else {
                resp.status(400);
                return UserView.putView(FAIL, "Empty or Missing Parameters.", null);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 23505) {
                resp.status(409);
                return UserView.putView(FAIL, "User With That Name Already Exists!", null);
            } else {
                logger.error("USER PUT", e);
                resp.status(500);
                return UserView.putView(FAIL, "Internal Server Error", null);
            }
        }
    };

    public static Route POST = (req, resp) -> {
        String userName = req.queryParams("userName");
        String password = req.queryParams("password");
        String logout =  req.queryParams("logout");

        if (logout != null && !logout.isEmpty() && logout.equals("true")) {
            logout(req);
            return UserView.deleteView(OK, "");
        } else {
            try {
                final User user = req.session().attribute("user");

                if (userName != null && !userName.isEmpty()) {
                    user.setUserName(userName);
                }
                if (password != null && !password.isEmpty()) {
                    user.setPassword(password);
                }

                user.save();
                login(req, user);
                return UserView.postView(OK, "", user);
            } catch (SQLException e) {
                if (e.getErrorCode() == 23505) {
                    resp.status(409);
                    return UserView.postView(FAIL, "User With That Name Already Exists!", null);
                } else {
                    logger.error("USER POST", e);
                    resp.status(500);
                    return UserView.postView(FAIL, "Internal Server Error", null);
                }
            }
        }
    };

    public static Route DELETE = (req, resp) -> {
        User user = req.attribute("user");

        try {
            user.delete();
            logout(req);
            return UserView.deleteView(OK, "");
        } catch (SQLException e) {
            logger.error("USER DELETE", e);
            resp.status(500);
            return UserView.deleteView(FAIL, "Internal Server Error");
        }
    };

    private static void login(final Request req, final User user) {
        req.session().attribute("user", user);
    }

    private static void logout(final Request req) {
        req.session().removeAttribute("user");
    }
}
