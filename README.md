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
 the underlying [News API][1]. All of the features of the underlying API
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

## API
### Search Endpoint

This endpoint allows finding articles by keyword(s).

Accessing this endpoint via a ```GET``` call allows for a retrieval of
a list of articles related to the keyword(s).

Endpoint: ```https://localhost/search```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one. |
| keywords  | Yes       | null    | The search keyword(s) to use. |

### Articles Endpoint

This endpoint allows for retrieval of articles based on specified
 constraints.

Accessing this endpoint a ```GET``` call allows for retrieval of
 articles based on the specified parameters.

```GET: https://localhost/articles```

| Parameter  | Required? | Default | Description |
|------------|-----------|---------|-------------|
| apiKey     | YES       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one. |
| source     | No        | random  | The id of the news source to use. See the [Sources Endpoint](#sources-endpoint) for supported sources. |
| sortBy     | No        | top     | The article list to pull from the source. The options are "top", "latest", and "popular". See the [Sources Endpoint](#sources-endpoint) for more information. |
| category   | No        | random  | The category to pick the articles from if the source is random. See the [Categories Endpoint](#categories-endpoint) for supported categories. |
| resultsAmt | No        | 0       | The amount of articles to display in the results. 0 for as many as are available. |

### User Endpoint

This endpoint allows for the creation of a user, change of a users
 password, and retrieval of a users API key.

Accessing this endpoint via a ```GET``` call allows for retrieval of a users
 API key.

```GET: https://localhost/user```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| userName  | Yes       | null    | The user name of the user. |
| password  | Yes       | null    | The password of the user. |

Accessing this endpoint via a ```POST``` call allows for changing a users name
 and/or password.

```POST: https://localhost/user```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one. |
| userName  | Yes/No    | null    | The new user name of the user. |
| password  | Yes/No    | null    | The new password of the user. |

Accessing this endpoint via a ```PUT``` call allows for creating a user.

```PUT: https://localhost/user```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| userName  | Yes       | null    | The user name of the user. |
| password  | Yes       | null    | The password of the user. |

Accessing this endpoint via a ```DELETE``` call allows for deleting a user
 and all data associated with the user.

```DELETE: https://localhost/user```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one. |

### Preference Endpoint

This endpoint allows for the retrieval of read and favourite articles. It
 also allows setting an article as favorite, read, or both.

Accessing this endpoint via a ```GET``` call allows for the retrieval of
 articles that match the specified parameters.

```GET: https://localhost/preference```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one. |
| favorite  | No        | yes     | Yes or No. Retrieve articles that are favorite. |
| read      | No        | yes     | Yes or No. Retrieve articles that were read. |

Accessing this endpoint via a ```POST``` call allows for setting an
 article as read, favorite, both, or neither.

```POST: https://localhost/preference```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one. |
| articleId | Yes       | null    | The id of an article. |
| favorite  | No        | null    | Yes or No. Marks the article as favorite or not. |
| read      | No        | null    | Yes or No. Marks te article as read or not. |

### History Endpoint

This endpoint allows for retrieval of all API calls made using the
 specified API key.

Accessing this endpoint via a ```GET``` call allows for the retrieval of
all API calls made using the specified API key.

```GET: https://localhost/history```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one. |

### Recommendation Endpoint

This endpoint returns a single article based previously favored and read
 articles.

Accessing this endpoint via a ```GET``` call allows for the retrieval of
 a single article based on previously favored and read articles.

```GET: https://localhost/recommend```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one.|

### Sources Endpoint

This endpoint returns all the supported new sources.

Accessing this endpoint via a ```GET``` call allows for the retrieval of
 all of the supported news sources.

```GET: https://localhost/sources```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one. |
| category  | No        | ALL     | The id of the category to get the sources for. See the [Category Endpoint](#category-endpoint) for supported categories. |
| language  | No        | ALL     | The 2-letter ISO-639-1 code of the language to get the sources for. See the [Language Endpoint](#language-endpoint) for supported languages. |
| country   | No        | ALL     | The 2-letter ISO 3166-1 code of the country to get the sources for. See the [Country Endpoint](#country-endpoint) for supported countries. |

### Category Endpoint

This endpoint returns all the supported categories of news sources.

Accessing this endpoint via a ```GET``` call allows for the retrieval of
 all of the supported categories of news sources.

```GET: https://localhost/category```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one.|

### Language Endpoint

This endpoint returns all the supported languages of news sources.

Accessing this endpoint via a ```GET``` call allows for the retrieval of
 all of the supported languages of news sources.

```GET: https://localhost/language```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one.|

### Country Endpoint

This endpoint returns all the supported countries of news sources.

Accessing this endpoint via a ```GET``` call allows for the retrieval of
 all of the supported countries of news sources.

```GET: https://localhost/country```

| Parameter | Required? | Default | Description |
|-----------|-----------|---------|-------------|
| apiKey    | Yes       | null    | Your API key. See [User Endpoint](#user-endpoint) for how to obtain one.|

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