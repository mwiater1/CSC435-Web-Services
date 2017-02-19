# Newsly

Newsly is a news aggregator powered by the [News API][1]. It builds
 upon the API by providing features which are not initially available.
 Such features include personalized recommendations and random article
 requests.

This application is built as a semester long project for my CSC435 Web
 Services class. Therefore the structure of this project is as follows,
 every module is a homework for the class. Each module may consist of
 just part of the project implemented or the entire project implemented
 in a different framework. This description will be later updated to
 specify the purpose of each module.

###### Modules
- hw1 - Homework 1
- hw2 - Homework 2
- hw3 - Homework 3
- hw4 - Homework 4
- hw5 - Homework 5
- hw6 - Homework 6
- hw7 - Homework 7

### Features
All of the features of Newsly will be available through the REST API as
 well as the Web UI. The features listed below are ones not offered by
 the underlying [New API][1]. All of the features of the underlying API
 will be available by default from Newsly.

- Article search.
    - The ability to request an article(s) by a keyword(s).
- Random article.
    - The ability to request a random article with no specification or
    by outlet/genera.
- User registration.
    - The ability to register a user to store personalized information.
- Article favorite.
    - The ability to favorite an article. This can only be done by a
    registered user. Users shall then be able to view a list of their
    favorite articles.
- Article recommendation.
    - The ability to recommend an article(s) based on past articles
    favored by the user.
- Article read marker.
    - The ability to mark an article as read and not have that article
    show up in future results. This can only be done by a registered
    user.

### API
- List API endpoints and examples

### Web Interface
- List UI features

[1]: https://newsapi.org/