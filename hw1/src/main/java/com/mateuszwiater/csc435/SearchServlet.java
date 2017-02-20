package com.mateuszwiater.csc435;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Mateusz Wiater
 */
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keywords = req.getParameter("keywords");

        // Remove null
        if(keywords == null) {
            keywords = "";
        }

        // Display search query
        final PrintWriter out = resp.getWriter();

        out.println("<p>You searched for an article using the following word(s): " + keywords + "</p>");
    }
}
