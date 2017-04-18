package services;

import models.Category;
import models.Country;
import models.Language;
import models.Source;
import play.Logger;
import play.api.Environment;
import play.api.inject.ApplicationLifecycle;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class Bootstrap {

    @Inject
    public Bootstrap(Environment environment) throws MalformedURLException {

        Map<String,URL> logos = new HashMap<>();
        logos.put("small",new URL("http://i.newsapi.org/abc-news-au-s.png"));
        logos.put("medium",new URL("http://i.newsapi.org/abc-news-au-m.png"));
        logos.put("large",new URL("http://i.newsapi.org/abc-news-au-l.png"));
        List<String> sortBysAvailable = new ArrayList<>();
        sortBysAvailable.add("top");
        new Source("abc-news-au","ABC News (AU)","Australia's most trusted source of local, national and world news. Comprehensive, independent, in-depth analysis, the latest business, sport, weather and more.",
                new URL("http://www.abc.net.au/news"), new Category("general"),new Language("en"),
                new Country("au"),logos, sortBysAvailable).save();

        logos = new HashMap<>();
        logos.put("small",new URL("http://i.newsapi.org/al-jazeera-english-s.png"));
        logos.put("medium",new URL("http://i.newsapi.org/al-jazeera-english-m.png"));
        logos.put("large",new URL("http://i.newsapi.org/al-jazeera-english-l.png"));
        sortBysAvailable = new ArrayList<>();
        sortBysAvailable.add("top");
        sortBysAvailable.add("latest");
        new Source("al-jazeera-english","Al Jazeera English","News, analysis from the Middle East and worldwide, multimedia and interactives, opinions, documentaries, podcasts, long reads and broadcast schedule.",
                new URL("http://www.aljazeera.com"), new Category("general"), new Language("en"),new Country("us"),logos, sortBysAvailable).save();
    }
}
