package com.mateuszwiater.csc435.controller;

import com.google.common.collect.Lists;
import com.mateuszwiater.csc435.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class ArticlesController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sourceParam = formatParameter(req.getParameter("source"));
        String sortByParam = formatParameter(req.getParameter("sortby"));
        String categoryParam = formatParameter(req.getParameter("category"));
        String apiKey = (String) req.getSession().getAttribute("apiKey");

        if(apiKey == null) {
            resp.sendRedirect("/hw3");
        } else {
            try {
                Source source;
                String sortBy;
                Optional<Source> src = Source.getSource(sourceParam);
                Optional<Category> cat = Category.getCategory(categoryParam);

                // If source exists
                if(src.isPresent()) {
                    source = src.get();
                } else {
                    Category category;
                    // Choose the category
                    if(cat.isPresent()) {
                        category = cat.get();
                    } else {
                        List<Category> categories = Category.getCategories();
                        category = categories.get(ThreadLocalRandom.current().nextInt(categories.size()));
                    }

                    List<Source> sources = Source.getSources(category, null, null);
                    source = sources.get(ThreadLocalRandom.current().nextInt(sources.size()));
                }

                // Choose the sortBy
                if(source.getSortBysAvailable().contains(sortByParam)) {
                    sortBy = sortByParam;
                } else {
                    sortBy = source.getSortBysAvailable().get(ThreadLocalRandom.current().nextInt(source.getSortBysAvailable().size()));
                }

                // Build preference map
                Map<String,Preference> preferenceMap = Preference.getPreferences(apiKey).stream().collect(Collectors.toMap(Preference::getArticleId, Function.identity()));

                req.getSession().setAttribute("selected_source", source.getId());
                req.getSession().setAttribute("selected_category", source.getCategory());
                req.getSession().setAttribute("selected_sortby", sortBy);
                req.getSession().setAttribute("sources", Source.getSources(null,null,null));
                req.getSession().setAttribute("sortbys", Arrays.asList("top","latest","popular"));
                req.getSession().setAttribute("categories", Category.getCategories());
                req.getSession().setAttribute("articles", Lists.partition(Article.getArticles(source, sortBy),3));
                req.getSession().setAttribute("preferenceMap", preferenceMap);
                req.getRequestDispatcher("/view/ArticlesView.jsp").forward(req, resp);
            } catch (SQLException e) {
                logger.error("ARTICLES GET", e);
                req.getSession().removeAttribute("apiKey");
                resp.sendRedirect("/hw3");
            }
        }
    }

    private String formatParameter(final String param) {
        return param == null || param.isEmpty() ? "any" : param;
    }
}
