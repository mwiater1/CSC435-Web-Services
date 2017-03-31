package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
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
                    resp.sendError(404,"User Not Found!");
                }
            } catch (SQLException e) {
                logger.error("GET ERROR", e);
                resp.sendError(500);
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
                    resp.sendError(409, "User with that name already exists!");
                } else {
                    logger.error("POST ERROR", e);
                    resp.sendError(500);
                    logout(req);
                }
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        if(!(userName == null || password == null || userName.isEmpty() || password.isEmpty())) {
            try {
                final User user = new User(userName,password);
                user.save();
                login(req, user);
            } catch (SQLException e) {
                if (e.getErrorCode() == 23505) {
                    resp.sendError(409, "User with that name already exists!");
                } else {
                    logger.error("PUT ERROR", e);
                    resp.sendError(500);
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
                logger.error("DELETE ERROR", e);
                resp.sendError(500);
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
