package controllers;

import actions.Auth;
import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.ArticlesView;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@With(Auth.class)
@SuppressWarnings("Duplicates")
public class ArticlesController extends Controller {

    public Result doGet() {
        String sourceParam = formatParam(request().getQueryString("source"));
        String sortByParam = formatParam(request().getQueryString("sortby"));
        String categoryParam = formatParam(request().getQueryString("category"));

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
        Map<Long, Preference> preferenceMap = Preference.getPreferences(UUID.fromString(session("apiKey")))
                .stream().collect(Collectors.toMap(Preference::getArticleId, Function.identity()));

        return ok(ArticlesView.render(Article.getArticles(source, sortBy), preferenceMap,
                Source.getSources(null, null, null), Category.getCategories(),
                Arrays.asList("top", "latest", "popular"), source.getId(), source.getCategory(), sortBy));
    }

    private String formatParam(final String param) {
        return Optional.ofNullable(param).orElse("any");
    }
}
