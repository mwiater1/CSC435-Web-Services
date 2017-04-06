<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="com.mateuszwiater.csc435.model.Article"%>
<%@ page import="com.mateuszwiater.csc435.model.Preference"%>
<% List<List<Article>> articles = (List<List<Article>>) request.getSession().getAttribute("articles"); %>
<% boolean favorite = ((boolean) request.getSession().getAttribute("favorite")); %>
<% boolean read = ((boolean) request.getSession().getAttribute("read")); %>
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
                    <li><a href="/hw2/articles">Articles</a></li>
                    <li><a href="/hw2/recommendation">Recommendation</a></li>
                    <li class="active"><a href="/hw2/preference">Preferences</a></li>
                </ul>
            </div>
        </nav>

        <div class="container">
            <div class="row card">
                <div class="card-content">
                    <form action="" method="get">
                        <div class="col s4">
                            <p>
                                <input type="checkbox" id="favorite" name="favorite" value="true" <%= favorite ? "checked" : "" %>/>
                                <label for="favorite">Favorite?</label>
                            </p>
                        </div>
                        <div class="col s4">
                            <p>
                                <input type="checkbox" id="read" name="read" value="true" <%= read ? "checked" : "" %> />
                                <label for="read">Read?</label>
                            </p>
                        </div>
                        <div class="col s4">
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
                            location.reload();
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
                            location.reload();
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
                            location.reload();
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
                            location.reload();
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