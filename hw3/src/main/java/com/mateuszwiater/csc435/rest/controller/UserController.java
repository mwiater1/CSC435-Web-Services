package com.mateuszwiater.csc435.rest.controller;

import com.google.common.base.Splitter;
import com.mateuszwiater.csc435.model.User;
import com.mateuszwiater.csc435.rest.util.Auth;
import com.mateuszwiater.csc435.rest.view.UserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import static com.mateuszwiater.csc435.rest.util.Response.Status.FAIL;
import static com.mateuszwiater.csc435.rest.util.Response.Status.OK;

@SuppressWarnings("Duplicates")
public class UserController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try {
                if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
                    final Optional<User> user = User.getUser(userName, password);

                    if(user.isPresent()) {
                        out.print(UserView.getView(OK, "", user.get()));
                    } else {
                        resp.setStatus(404);
                        out.print(UserView.getView(FAIL, "User Not Found!", null));
                    }
                } else {
                    resp.setStatus(400);
                    out.print(UserView.getView(FAIL, "Empty or Missing Parameters.", null));
                }
            } catch (SQLException e) {
                logger.error("USER GET", e);
                resp.setStatus(500);
                out.print(UserView.getView(FAIL, "Internal Server Error", null));
            }
        } catch (IOException e) {
            logger.error("USER GET", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String apiKey = req.getParameter("apiKey");

        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try {
                Auth.AuthResponse authResponse = Auth.authenticate(apiKey);
                if (authResponse.isAuthenticated()) {
                    final User user = authResponse.getUser();

                    if (userName != null && !userName.isEmpty()) {
                        user.setUserName(userName);
                    }
                    if (password != null && !password.isEmpty()) {
                        user.setPassword(password);
                    }

                    user.save();
                    out.print(UserView.postView(OK, "", user));
                } else {
                    resp.setStatus(400);
                    out.print(authResponse.toJson());
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 23505) {
                    resp.setStatus(409);
                    out.print(UserView.postView(FAIL, "User With That Name Already Exists!", null));
                } else {
                    logger.error("USER POST", e);
                    resp.setStatus(500);
                    out.print(UserView.postView(FAIL, "Internal Server Error", null));
                }
            }
        } catch (IOException e) {
            logger.error("USER POST", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try (BufferedReader b = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
                Map<String, String> dataMap = Splitter.on('&').trimResults()
                        .withKeyValueSeparator(Splitter.on('=')
                                .limit(2)
                                .trimResults())
                        .split(b.readLine());
                final String userName = dataMap.get("userName");
                final String password = dataMap.get("password");

                if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
                    final User user = new User(userName, password);
                    user.save();
                    out.print(UserView.putView(OK, "", user));
                } else {
                    resp.setStatus(400);
                    out.print(UserView.putView(FAIL, "Empty or Missing Parameters.", null));
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == 23505) {
                    resp.setStatus(409);
                    out.print(UserView.putView(FAIL, "User With That Name Already Exists!", null));
                } else {
                    logger.error("USER PUT", e);
                    resp.setStatus(500);
                    out.print(UserView.putView(FAIL, "Internal Server Error", null));
                }
            } catch (IllegalArgumentException | NullPointerException e) {
                resp.setStatus(400);
                out.print(UserView.putView(FAIL, "Empty or Missing Parameters.", null));
            }
        } catch (IOException e) {
            logger.error("USER PUT", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String apiKey = req.getParameter("apiKey");

        resp.setContentType("application/json; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            try {
                Auth.AuthResponse authResponse = Auth.authenticate(apiKey);
                if (authResponse.isAuthenticated()) {
                    authResponse.getUser().delete();
                    out.print(UserView.deleteView(OK, ""));
                } else {
                    resp.setStatus(400);
                    out.print(authResponse.toJson());
                }
            } catch (SQLException e) {
                logger.error("USER DELETE", e);
                resp.setStatus(500);
                out.print(UserView.deleteView(FAIL, "Internal Server Error"));
            }
        } catch (IOException e) {
            logger.error("USER DELETE", e);
        }
    }
}
