package com.mateuszwiater.csc435;

import com.mateuszwiater.csc435.controller.*;
import com.mateuszwiater.csc435.controller.ArticlesController;
import com.mateuszwiater.csc435.controller.PreferenceController;
import com.mateuszwiater.csc435.controller.SourcesController;
import com.mateuszwiater.csc435.controller.UserController;
import com.mateuszwiater.csc435.view.AuthView;
import com.mateuszwiater.csc435.util.Util;
import freemarker.template.Configuration;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.mateuszwiater.csc435.util.Response.Status.FAIL;
import static spark.Spark.*;

public class Init {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Configuration cfg = new Configuration(new Version("2.3.23"));
        File f = new File((new Init()).getClass().getClassLoader().getResource("templates").toURI());
        cfg.setDirectoryForTemplateLoading(f);
        cfg.setAPIBuiltinEnabled(true);
        Util.setConfiguration(cfg);

        port(8081);

        before("/hw7/restful/*", (req, resp) -> resp.type("application/json; charset=UTF-8"));

        before((req, resp) -> {
            if(req.session().attribute("user") == null) {
                if(!(req.pathInfo().matches("/hw7|/hw7/restful/user") && req.requestMethod().matches("GET|PUT"))) {
                    if(req.pathInfo().startsWith("/hw7/restful")) {
                        halt(401, AuthView.getView(FAIL, "User Unauthenticated!"));
                    } else {
                        resp.redirect("/hw7");
                        halt();
                    }
                }
            }
        });

        path("/hw7", () -> {
            get("", UserController.GET);
            put("", UserController.PUT);
            post("", UserController.POST);
            delete("", UserController.DELETE);

            get("/sources", SourcesController.GET);
            get("/articles", ArticlesController.GET);
            get("/recommend", RecommendationController.GET);
            get("/preference", PreferenceController.GET);
            post("/preference", PreferenceController.POST);

            path("/restful", () -> {
                get("/language", RestfulLanguageController.GET);
                get("/country", RestfulCountryController.GET);
                get("/category", RestfulCategoryController.GET);
                get("/sources", RestfulSourcesController.GET);
                get("/recommend", RestfulRecommendController.GET);
                get("/articles", RestfulArticlesController.GET);
                get("/preference", RestfulPreferenceController.GET);
                post("/preference", RestfulPreferenceController.POST);

                get("/user", RestfulUserController.GET);
                put("/user", RestfulUserController.PUT);
                post("/user", RestfulUserController.POST);
                delete("/user", RestfulUserController.DELETE);

                get("/*", NotFoundController.REST);
                put("/*", NotFoundController.REST);
                post("/*", NotFoundController.REST);
                delete("/*", NotFoundController.REST);
            });
        });

        get("/*", NotFoundController.UI);
        put("/*", NotFoundController.UI);
        post("/*", NotFoundController.UI);
        delete("/*", NotFoundController.UI);
    }
}
