package com.mateuszwiater.csc435.model;

import java.util.List;
import java.util.Map;

public class Source {
    private List<String> sortBysAvailable;
    private Map<String, String> urlsToLogos;
    private String id, name, description, url, category, language, country;

    public static ModelResponse<List<Source>> getSources(final Category category, final Language language, final Country country) {
        String query = "SELECT * FROM SOURCES";

        return null;
    }

    public Source(String id, String name, String description, String url, String category, String language,
                  String country, Map<String, String> urlsToLogos, List<String> sortBysAvailable) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.country = country;
        this.language = language;
        this.category = category;
        this.description = description;
        this.urlsToLogos = urlsToLogos;
        this.sortBysAvailable = sortBysAvailable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public Map<String, String> getUrlsToLogos() {
        return urlsToLogos;
    }

    public List<String> getSortBysAvailable() {
        return sortBysAvailable;
    }
}