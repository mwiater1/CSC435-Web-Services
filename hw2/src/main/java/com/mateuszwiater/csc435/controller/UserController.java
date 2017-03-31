package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String apiKey = (String) req.getSession().getAttribute("apiKey");

        System.out.println("USERNAME: " + userName + " PASS: " + password + " API: " + apiKey);

        if(!(userName == null || password == null)) {
            try {
                final Optional<User> user = User.getUser(userName, password);

                if(user.isPresent()) {
                    req.getSession().setAttribute("apiKey", user.get().getApiKey());
                    req.getSession().setAttribute("userName", user.get().getUserName());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                req.getSession().removeAttribute("apiKey");
            }
        } else if(apiKey != null) {
            try {
                final Optional<User> user = User.getUser(UUID.fromString(apiKey));

                if(user.isPresent()) {
                    req.getSession().setAttribute("apiKey", user.get().getApiKey());
                    req.getSession().setAttribute("userName", user.get().getUserName());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                req.getSession().removeAttribute("apiKey");
            }
        } else {
            System.out.println("ELSE");
        }

        System.out.println("GET: " + req.getSession().getId());
        req.getRequestDispatcher("/view/UserView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String logout = req.getParameter("logout");
        System.out.println("DELETE: " + req.getSession().getId());
        if (logout != null) {
            System.out.println("LOGOUT");
            this.logout(req);
        }
    }

    private void login(final HttpServletRequest req, final User user) {
        req.getSession().setAttribute("apiKey", user.getApiKey());
        req.getSession().setAttribute("userName", user.getUserName());
    }

    private void logout(final HttpServletRequest req) {
        req.getSession().removeAttribute("apiKey");
        req.getSession().removeAttribute("userName");
        req.
    }
}
