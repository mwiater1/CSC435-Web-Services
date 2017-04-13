package models;

import java.util.ArrayList;
import java.util.List;

public class Article {
    private String id, sortBy, author, title, description, url, urlToImage, source, publishedAt;

    public Article(String id, String author, String title, String description, String url, String urlToImage, String source, String sortBy, String publishedAt) {
        this.id = id;
        this.sortBy = sortBy;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.source = source;
        this.publishedAt = publishedAt;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getSource() {
        return source;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSortBy() {
        return sortBy;
    }

    public static List<Article> getArticles() {
        final List<Article> articles = new ArrayList<>();
        articles.add(new Article("1","http://www.abc.net.au/news/david-lipson/7849048",
                "'My hand on their electoral throat': Glenn Druery lifts lid on One Nation vendetta",
                "Self-styled preference whisperer Glenn Druery lifts the lid on a personal vendetta against One Nation that he claims goes back almost two decades.",
                "http://www.abc.net.au/news/2017-04-04/preference-whisperer-glenn-druery-one-nation-micro-party-deals/8415276",
                "http://www.abc.net.au/news/image/7566444-1x1-700x700.jpg",
                "abc-news-au",
                "top",
                "2017-04-04T13:33:10Z"));

        articles.add(new Article("2","http://www.abc.net.au/news/5511636",
                "High Court to rule if Bob Day was validly elected",
                "The High Court will today rule on whether former South Australian senator Bob Day was validly elected to Parliament last year, and how his Upper House crossbench seat should be filled.",
                "http://www.abc.net.au/news/2017-04-05/high-court-to-rule-if-bob-day-was-validly-elected/8413566",
                "http://www.abc.net.au/news/image/7986820-1x1-700x700.jpg",
                "abc-news-au",
                "top",
                "2017-04-04T14:14:49Z"));

        articles.add(new Article("3","Hamza Mohamed",
                "The Gambia: Reviving the tourism industry",
                "Tourists stopped coming during the country's electoral crisis, but the new government is hopeful good times are ahead.",
                "http://www.aljazeera.com/indepth/features/2017/03/gambia-reviving-tourism-industry-170313122806481.html",
                "http://www.aljazeera.com/mritems/Images/2017/4/5/e0475dec96c14c479eca2d4ebe0ebc5b_18.jpg",
                "al-jazeera-english",
                "top",
                "2017-04-06T07:44:00Z"));

        articles.add(new Article("4","Lucia Benavides",
                "Murdered for being women: Spain tackles femicide rates",
                "Women are protesting as rates of violence against them rise, forcing the government to act.",
                "http://www.aljazeera.com/indepth/features/2017/03/murdered-women-spain-tackles-femicide-rates-170319132509999.html",
                "http://www.aljazeera.com/mritems/Images/2017/3/30/2d765e61e85b4f0fa38f0015cc5e8730_18.jpg",
                "al-jazeera-english",
                "top",
                "2017-04-05T06:09:00Z"));

        articles.add(new Article("5","Nicolas Haque",
                "Change is coming fast in The Gambia - and it's intoxicating",
                "As many young Gambians get to participate in their first free and fair election, an unprecedented sense of ownership over the country's future is fast emerging.",
                "http://www.aljazeera.com/blogs/africa/2017/04/change-coming-fast-gambia-intoxicating-170405235914726.html",
                "http://www.aljazeera.com/mritems/Images/2017/4/5/5246eba96d0e4150939093be09a0cc89_18.jpg",
                "al-jazeera-english",
                "latest",
                "2017-04-06T07:55:00Z"));

        articles.add(new Article("6","Joseph Stepansky",
                "What is national concealed carry reciprocity?",
                "If passed, the law would force every town to honour gun laws of the most permissive states for people from those states.",
                "http://www.aljazeera.com/indepth/features/2017/03/national-concealed-carry-reciprocity-170330103123777.html",
                "http://www.aljazeera.com/mritems/Images/2017/4/3/571f2404d9be4e8aacad9e606aa66db4_18.jpg",
                "al-jazeera-english",
                "latest",
                "2017-04-06T06:46:42Z"));
        return articles;
    }
}
