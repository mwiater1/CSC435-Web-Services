package com.mateuszwiater.csc435.controller;

import com.mateuszwiater.csc435.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mateuszwiater.csc435.util.Util.render;

@SuppressWarnings("Duplicates")
public class ArticlesController {
    private static Logger logger = LoggerFactory.getLogger(ArticlesController.class);

    public static Route GET = (req, resp) -> {
        String sourceParam = formatParameter(req.queryParams("source"));
        String sortByParam = formatParameter(req.queryParams("sortby"));
        String categoryParam = formatParameter(req.queryParams("category"));
        String apiKey = ((User)req.session().attribute("user")).getApiKey();

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

            Map<String,Object> model = new HashMap<>();
            model.put("currentPage", "articles");
            model.put("selectedSource", source.getId());
            model.put("selectedCategory", source.getCategory());
            model.put("selectedSortBy", sortBy);
            model.put("sources", Source.getSources(null,null,null));
            model.put("sortBys", Arrays.asList("top","latest","popular"));
            model.put("categories", Category.getCategories());
            model.put("articles", Article.getArticles(source, sortBy));
            model.put("preferenceMap", preferenceMap);

            return render(model, "articleView.ftl");
        } catch (SQLException e) {
            logger.error("ARTICLES GET", e);
            resp.status(500);
            return "Internal Server Error.";
        }
    };

    private static String formatParameter(final String param) {
        return param == null || param.isEmpty() ? "any" : param;
    }
}
