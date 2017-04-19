package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Article;
import models.Source;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import play.Logger;
import play.api.Environment;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Singleton
public class Bootstrap {

    @Inject
    public Bootstrap(Environment environment) {
        try {
            if (Source.getSources(null, null, null).isEmpty()) {
                URI uri = new URIBuilder().setScheme("https")
                        .setHost("newsapi.org")
                        .setPath("/v1/sources")
                        .build();

                try (InputStreamReader sReader = new InputStreamReader(HttpClients.createDefault().execute(new HttpGet(uri)).getEntity().getContent())) {
                    Gson g = new Gson();
                    final Type sourcesType = new TypeToken<SourcesBootstrap>() {
                    }.getType();
                    SourcesBootstrap sBootstrap = g.fromJson(sReader, sourcesType);

                    for (Source source : sBootstrap.getSources()) {
                        source.save();
                        Logger.info("Loading Source: " + source.getName());

                        for (String s : source.getSortBysAvailable()) {
                            uri = new URIBuilder().setScheme("https")
                                    .setHost("newsapi.org")
                                    .setPath("/v1/articles")
                                    .setParameter("apiKey", "c923e97e84ff46e6a7657a6b48995b6e")
                                    .setParameter("source", source.getId())
                                    .setParameter("sortBy", s)
                                    .build();

                            Logger.info("\tLoading SortBy: " + s);
                            try (InputStreamReader aReader = new InputStreamReader(HttpClients.createDefault().execute(new HttpGet(uri)).getEntity().getContent())) {
                                final Type articlesType = new TypeToken<ArticlesBootstrap>() {
                                }.getType();
                                ArticlesBootstrap aBootstrap = g.fromJson(aReader, articlesType);

                                for (Article article : aBootstrap.getArticles()) {
                                    article.setSortBy(s);
                                    article.setSourceId(source.getId());
                                    article.save();
                                }
                            }
                        }
                    }

                    Logger.info("Data Successfully Loaded!");
                }
            }
        } catch (URISyntaxException | IOException e) {
            Logger.error("ERROR LOADING DATA");
        }
    }

    private class SourcesBootstrap {
        private String status;
        private List<Source> sources;

        public String getStatus() {
            return status;
        }

        public List<Source> getSources() {
            return sources;
        }
    }

    private class ArticlesBootstrap {
        private String status, source, sortBy;
        private List<Article> articles;

        public String getStatus() {
            return status;
        }

        public String getSource() {
            return source;
        }

        public String getSortBy() {
            return sortBy;
        }

        public List<Article> getArticles() {
            return articles;
        }
    }
}
