package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Source {
    private List<String> sortBysAvailable;
    private Map<String, String> urlsToLogos;
    private String id, name, description, url, category, language, country;

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

    public static List<Source> getSources() {
        final List<Source> sources = new ArrayList<>();

        Map<String,String> logos = new HashMap<>();
        logos.put("small","http://i.newsapi.org/abc-news-au-s.png");
        logos.put("medium","http://i.newsapi.org/abc-news-au-m.png");
        logos.put("large","http://i.newsapi.org/abc-news-au-l.png");
        List<String> sortBysAvailable = new ArrayList<>();
        sortBysAvailable.add("top");
        sources.add(new Source("abc-news-au","ABC News (AU)","Australia's most trusted source of local, national and world news. Comprehensive, independent, in-depth analysis, the latest business, sport, weather and more.",
                "http://www.abc.net.au/news", "general","en","au",logos, sortBysAvailable));

        logos = new HashMap<>();
        logos.put("small","http://i.newsapi.org/al-jazeera-english-s.png");
        logos.put("medium","http://i.newsapi.org/al-jazeera-english-m.png");
        logos.put("large","http://i.newsapi.org/al-jazeera-english-l.png");
        sortBysAvailable = new ArrayList<>();
        sortBysAvailable.add("top");
        sortBysAvailable.add("latest");
        sources.add(new Source("al-jazeera-english","Al Jazeera English","News, analysis from the Middle East and worldwide, multimedia and interactives, opinions, documentaries, podcasts, long reads and broadcast schedule.",
                "http://www.aljazeera.com", "general","en","us",logos, sortBysAvailable));

        return sources;
    }
}