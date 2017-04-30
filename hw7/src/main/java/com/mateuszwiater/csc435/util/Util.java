package com.mateuszwiater.csc435.util;

import freemarker.template.Configuration;
import freemarker.template.Version;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.Map;

public class Util {
    private volatile static Configuration CFG = new Configuration(new Version("2.3.23"));

    public static void setConfiguration(final Configuration configuration) {
        CFG = configuration;
    }

    public static String render(Map<String, Object> model, String templatePath) {
        return new FreeMarkerEngine(CFG).render(new ModelAndView(model, templatePath));
    }
}
