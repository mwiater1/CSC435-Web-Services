package com.mateuszwiater.csc435;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mateusz Wiater
 */
public class UserServlet extends HttpServlet {
    private Map<String, Map<String, String>> sessions;

    @Override
    public void init() throws ServletException {
        sessions = new HashMap<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String sessionId = req.getSession().getId();

        // Remove null
        if (userName == null) {
            userName = "";
        }

        if(password == null) {
            password = "";
        }

        // Make sure there is a hash map for the current session.
        if(!sessions.containsKey(sessionId)) {
            sessions.put(sessionId, new HashMap<>());
        }

        // Add the user or update the users password as provided by the request in the current session.
        if(!userName.isEmpty() || !password.isEmpty()) {
            sessions.get(sessionId).put(userName, password);
        }

        // Display all of the users associated with this session and relevant information.
        final PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html><html><head><style>table {font-family: arial, sans-serif;border-collapse: collapse;}td, th{border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}</style></head><body>");
        out.println("<p>Current Session: " + sessionId + "<p>");

        out.println("<table><tr><th>User Name</th><th>Password</th></tr>");
        sessions.get(sessionId).entrySet().forEach(e -> {
            out.println("<tr><td>" + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>");
        });
        out.println("</table></body></html>");
    }
}
