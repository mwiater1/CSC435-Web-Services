<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="com.mateuszwiater.csc435.model.Source"%>
<%@ page import="com.mateuszwiater.csc435.model.Article"%>
<%@ page import="com.mateuszwiater.csc435.model.Country"%>
<%@ page import="com.mateuszwiater.csc435.model.Category"%>
<%@ page import="com.mateuszwiater.csc435.model.Language"%>
<%@ page import="com.mateuszwiater.csc435.model.Preference"%>
<% List<List<Article>> articles = (List<List<Article>>) request.getSession().getAttribute("articles"); %>
<% List<Category> categories = (List<Category>) request.getSession().getAttribute("categories"); %>
<% List<Source> sources = (List<Source>) request.getSession().getAttribute("sources"); %>
<% List<String> sortBys = (List<String>) request.getSession().getAttribute("sortbys"); %>
<% String selectedCategory = ((String) request.getSession().getAttribute("selected_category")); %>
<% String selectedSource = ((String) request.getSession().getAttribute("selected_source")); %>
<% String selectedSortBy = ((String) request.getSession().getAttribute("selected_sortby")); %>
<% Map<String,Preference> preferenceMap = ((Map<String,Preference>) request.getSession().getAttribute("preferenceMap")); %>
<!DOCTYPE html>
<html>
<head>
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import Google Roboto Font-->
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.1/css/materialize.min.css"  media="screen,projection"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <style>
        .full {
            width: 100%;
        }
        .btn {
            margin-bottom: 15px;
        }
    </style>
</head>

<body>
<nav>
    <div class="nav-wrapper">
        <a href="#" class="brand-logo right">Newsly</a>
        <ul class="left">
            <li><a href="/hw2/">User</a></li>
            <li><a href="/hw2/sources">Sources</a></li>
            <li class="active"><a href="/hw2/articles">Articles</a></li>
            <li><a href="/hw2/recommendation">Recommendation</a></li>
            <li><a href="/hw2/preference">Preferences</a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="row card">
        <div class="card-content">
            <form action="" method="get">
                <div class="input-field col s3">
                    <select name="source">
                        <option value="any" <%= selectedSource.equalsIgnoreCase("any") ? "selected" : "" %>>Any</option>
                        <% for(Source s : sources) { %>
                        <option value="<%= s.getId() %>" <%= selectedSource.equalsIgnoreCase(s.getId()) ? "selected" : "" %>><%= s.getName() %></option>
                        <% } %>
                    </select>
                    <label>Source</label>
                </div>
                <div class="input-field col s3">
                    <select name="sortby">
                        <option value="any" <%= selectedSortBy.equalsIgnoreCase("any") ? "selected" : "" %>>Any</option>
                        <% for(String s : sortBys) { %>
                        <option value="<%= s %>" <%= selectedSortBy.equalsIgnoreCase(s) ? "selected" : "" %>><%= s %></option>
                        <% } %>
                    </select>
                    <label>SortBy</label>
                </div>
                <div class="input-field col s3">
                    <select name="category">
                        <option value="any" <%= selectedCategory.equalsIgnoreCase("any") ? "selected" : "" %>>Any</option>
                        <% for(Category c : categories) { %>
                        <option value="<%= c.getName() %>" <%= selectedCategory.equalsIgnoreCase(c.getName()) ? "selected" : "" %>><%= c.getName() %></option>
                        <% } %>
                    </select>
                    <label>Category</label>
                </div>
                <div class="col s3">
                    <button class="btn waves-effect waves-light full" type="submit">Filter</button>
                </div>
            </form>
        </div>
    </div>

    <% for(List<Article> r : articles) { %>
    <div class="row">
        <% for(Article c : r) { %>
        <div class="col s12 m6 l4">
            <div class="card large hoverable">
                <!-- Card Content -->
                <div class="card-image waves-effect waves-block waves-light">
                    <img class="activator" src="<%= c.getUrlToImage()%>">
                </div>
                <div class="card-content">
                    <span class="activator grey-text text-darken-4"><%= c.getTitle() %><i class="material-icons right">more_vert</i></span>
                </div>
                <div class="card-reveal">
                    <span class="card-title grey-text text-darken-4"><%= c.getTitle() %><i class="material-icons right">close</i></span>
                    <p><%= c.getDescription() %></p>
                    <ul class="collection">
                        <li class="collection-item"><div>Source<span class="new badge" data-badge-caption="<%= c.getSource() %>"></span></div></li>
                        <li class="collection-item"><div>SortBy<span class="new badge" data-badge-caption="<%= c.getSortBy() %>"></span></div></li>
                        <li class="collection-item"><div>Published<span class="new badge" data-badge-caption="<%= c.getPublishedAt() %>"></span></div></li>
                        <li class="collection-item"><div>Author<span class="new badge" data-badge-caption="<%= c.getAuthor() %>"></span></div></li>
                    </ul>
                </div>
                <div class="card-action">
                    <a href="<%= c.getUrl() %>" class="waves-effect waves-light btn">Go To Article</a>
                    <a href="#">
                        <i class="material-icons small favorite-star right" data-articleid="<%= c.getId() %>"><%= preferenceMap.containsKey(c.getId()) && preferenceMap.get(c.getId()).isFavorite() ? "star" : "star_border" %></i>
                    </a>
                    <a href="#">
                        <i class="material-icons small read right" data-articleid="<%= c.getId() %>"><%= preferenceMap.containsKey(c.getId()) && preferenceMap.get(c.getId()).isRead() ? "drafts" : "markunread" %></i>
                    </a>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    <% } %>
</div>

<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.1/js/materialize.min.js"></script>
<script>
    $(document).ready(function() {
        $('select').material_select();
    });

    $('.favorite-star').click(function () {
        var obj = $(this);
        var articleId = obj.data('articleid');
        if($(this).html() === 'star') {
            $.ajax({
                url: '/hw2/preference',
                type: 'POST',
                data: {'favorite': 'false', 'articleId': articleId},
                success: function (result) {
                    obj.html('star_border');
                },
                error: function (xhr, textStatus, error) {
                    Materialize.toast(error, 4000);
                }
            });
        } else {
            $.ajax({
                url: '/hw2/preference',
                type: 'POST',
                data: {'favorite': 'true', 'articleId': articleId},
                success: function (result) {
                    obj.html('star');
                },
                error: function (xhr, textStatus, error) {
                    Materialize.toast(error, 4000);
                }
            });
        }
    });

    $('.read').click(function () {
        var obj = $(this);
        var articleId = obj.data('articleid');
        if($(this).html() === 'markunread') {
            $.ajax({
                url: '/hw2/preference',
                type: 'POST',
                data: {'read': 'true', 'articleId': articleId},
                success: function (result) {
                    obj.html('drafts');
                },
                error: function (xhr, textStatus, error) {
                    Materialize.toast(error, 4000);
                }
            });
        } else {
            $.ajax({
                url: '/hw2/preference',
                type: 'POST',
                data: {'read': 'false', 'articleId': articleId},
                success: function (result) {
                    obj.html('markunread');
                },
                error: function (xhr, textStatus, error) {
                    Materialize.toast(error, 4000);
                }
            });
        }
    });
</script>
</body>
</html>