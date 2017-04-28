package hw6

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mateuszwiater.csc435.domains.Article
import com.mateuszwiater.csc435.domains.Source
import com.mateuszwiater.csc435.services.SourceService
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClients
import org.h2.tools.Server

import java.lang.reflect.Type

class BootStrap {
    SourceService sourceService
    final String[] args = [
            "-tcpPort", "8092",
            "-tcpAllowOthers"]

    Server server

    def init = { servletContext ->
        server = Server.createTcpServer(args).start()
        try {
            if (sourceService.getSources(null, null, null).isEmpty()) {
                URI uri = new URIBuilder().setScheme("https")
                        .setHost("newsapi.org")
                        .setPath("/v1/sources")
                        .build()
                HttpClients.createDefault().execute(new HttpGet(uri)).getEntity().getContent().withReader { sReader ->
                    Gson g = new Gson()
                    final Type sourcesType = new TypeToken<SourcesBootstrap>() {}.getType()
                    SourcesBootstrap sBootstrap = g.fromJson(sReader, sourcesType)

                    for (Source source : sBootstrap.getSources()) {
                        source.save()
                        println("Loading Source: " + source.getName())

                        for (String s : source.getSortBysAvailable()) {
                            uri = new URIBuilder().setScheme("https")
                                    .setHost("newsapi.org")
                                    .setPath("/v1/articles")
                                    .setParameter("apiKey", "c923e97e84ff46e6a7657a6b48995b6e")
                                    .setParameter("source", source.getId())
                                    .setParameter("sortBy", s)
                                    .build()

                            println("\tLoading SortBy: " + s)

                            HttpClients.createDefault().execute(new HttpGet(uri)).getEntity().getContent().withReader {aReader ->
                                final Type articlesType = new TypeToken<ArticlesBootstrap>() {}.getType()
                                ArticlesBootstrap aBootstrap = g.fromJson(aReader, articlesType)

                                for (Article article : aBootstrap.getArticles()) {
                                    article.setSortBy(s)
                                    article.setSourceId(source.getId())
                                    article.save()
                                }
                            }
                        }
                    }

                    println("Data Successfully Loaded!")
                }
            }
        } catch (URISyntaxException | IOException e) {
            log.error("ERROR LOADING DATA")
        }
    }

    def destroy = {
        server.stop()
    }

    private class SourcesBootstrap {
        private String status;
        private List<Source> sources;

        String getStatus() {
            return status
        }

        List<Source> getSources() {
            return sources
        }
    }

    private class ArticlesBootstrap {
        private String status, source, sortBy
        private List<Article> articles

        String getStatus() {
            return status
        }

        String getSource() {
            return source
        }

        String getSortBy() {
            return sortBy
        }

        List<Article> getArticles() {
            return articles
        }
    }
}
