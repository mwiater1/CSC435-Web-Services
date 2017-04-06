package com.mateuszwiater.csc435.controller;

import com.google.common.base.Splitter;
import com.mateuszwiater.csc435.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            try {
                final Optional<User> user = User.getUser(userName, password);

                if(user.isPresent()) {
                    login(req, user.get());
                } else {
                    resp.setStatus(404);
                    resp.getWriter().print("User Not Found!");
                }
            } catch (SQLException e) {
                logger.error("USER GET", e);
                resp.setStatus(500);
                logout(req);
            }
        } else {
            req.getRequestDispatcher("/view/UserView.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String logout = req.getParameter("logout");

        String apiKey = (String) req.getSession().getAttribute("apiKey");
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
                    resp.setStatus(409);
                    resp.getWriter().print("User with that name already exists!");
                } else {
                    logger.error("USER POST", e);
                    resp.setStatus(500);
                    logout(req);
                }
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName, password;

        try (BufferedReader b = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
            Map<String, String> dataMap = Splitter.on('&').trimResults()
                    .withKeyValueSeparator(Splitter.on('=')
                                    .limit(2)
                                    .trimResults())
                    .split(b.readLine());

            userName = dataMap.get("userName");
            password = dataMap.get("password");
        }

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            try {
                final User user = new User(userName,password);
                user.save();
                login(req, user);
            } catch (SQLException e) {
                if (e.getErrorCode() == 23505) {
                    resp.sendError(409, "User with that name already exists!");
                } else {
                    logger.error("USER PUT", e);
                    resp.setStatus(500);
                    logout(req);
                }
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String apiKey = (String) req.getSession().getAttribute("apiKey");

        if (apiKey != null) {
            try {
                final Optional<User> user = User.getUser(UUID.fromString(apiKey));

                if(user.isPresent()) {
                    user.get().delete();
                    logout(req);
                }
            } catch (SQLException e) {
                logger.error("USER DELETE", e);
                resp.setStatus(500);
                logout(req);
            }
        }
    }

    private void login(final HttpServletRequest req, final User user) {
        req.getSession().setAttribute("apiKey", user.getApiKey());
        req.getSession().setAttribute("userName", user.getUserName());
    }

    private void logout(final HttpServletRequest req) {
        req.getSession().removeAttribute("apiKey");
        req.getSession().removeAttribute("userName");
    }
}
