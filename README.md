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
- API Use History
    - The ability to see a history of what calls were made using a
    specific API key.

### API
##### Search API Call

This endpoint allows finding articles by keyword.

Endpoint: ```https://localhost/search```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | No        | null    | The API key to associate this search query with. |
| keywords  | Yes       | null    | The search keyword(s) to use. |

##### Articles API Call

This endpoint allows for multiple or a single article to be returned
 and is filtered based on the specified options.

Endpoint: ```https://localhost/articles```

| Parameter  | Required? | Default | Description |
|------------|-----------|---------|-------------|
| apiKey     | No        | null    | The API key to associate this articles query with. |
| source     | No        | random  | The news source to use. |
| sortBy     | No        | top     | How the articles are sorted. |
| category   | No        | random  | The category to pick the article(s) from if the source is random. |
| resultsAmt | No        | 1       | The amount of articles to display in the results. |

##### User API Call

This endpoint allows for the creation of a user, change of a users
 password, and retrieval of a users API key.

Accessing this endpoint via a ```GET``` call allows for retrieval of a users
 API key.

GET: ```https://localhost/user```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| userName  | Yes       | null    | The user name of the user. |
| password  | Yes       | null    | The password of the user. |

Accessing this endpoint via a ```POST``` call allows for changing a users name
 and/or password.

POST: ```https://localhost/user```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | The API key associated with the user. |
| userName  | Yes/No    | null    | The user name of the user. |
| password  | Yes/No    | null    | The password of the user. |

Accessing this endpoint via a ```PUT``` call allows for creating a user.

PUT: ```https://localhost/user```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| userName  | Yes       | null    | The user name of the user. |
| password  | Yes       | null    | The password of the user. |

Accessing this endpoint via a ```DELETE``` call allows for deleting a user
 and all data associated with the user.

DELETE: ```https://localhost/user```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | The API key associated with the user. |

##### Article API Call

This endpoint marks the specified article as favorite, read, or neither.

Endpoint: ```https://localhost/article```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | The API key associated with an account. |
| articleId | Yes       | null    | The id of an article. |
| favorite  | No        | null    | Yes or No. Marks the article as favorite or not. |
| read      | No        | null    | Yes or No. Marks te article as read or not. |

##### History API Call

This endpoint returns the history of favored articles, read articles, or
 all API query's made with this API key.

Endpoint: ```https://localhost/history```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | The API key associated with an account. |
| type      | Yes       | all     | The type of history requested. Options: all, favorite, read |

##### Recommendation API Call

This endpoint return a single article based previously favored and read
 articles.

Endpoint: ```https://localhost/recommend```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | The API key associated with an account. |

### Web Interface

The user interface will implement all of the functionality of the API.
 The [MaterializeCSS][2] framework will be used for the look and feel of the
 interface.

### Technology Stack

The technology stack will change per requirements of each homework module.
 One thing that remain constant is that HTML, CSS, Javascript, and
 [MaterializeCSS][2] will be used for the user interface. Another thing
 that will remain constant is that Java will be used for the back end.

[1]: https://newsapi.org/
[2]: http://materializecss.com/