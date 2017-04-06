<%@ page import="java.util.List"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.mateuszwiater.csc435.model.Source"%>
<%@ page import="com.mateuszwiater.csc435.model.Country"%>
<%@ page import="com.mateuszwiater.csc435.model.Category"%>
<%@ page import="com.mateuszwiater.csc435.model.Language"%>
<% List<List<Source>> sources = (List<List<Source>>) request.getSession().getAttribute("sources"); %>
<% List<Category> categories = (List<Category>) request.getSession().getAttribute("categories"); %>
<% List<Language> languages = (List<Language>) request.getSession().getAttribute("languages"); %>
<% List<Country> countries = (List<Country>) request.getSession().getAttribute("countries"); %>
<% String selectedCategory = ((String) request.getSession().getAttribute("selected_category")); %>
<% String selectedLanguage = ((String) request.getSession().getAttribute("selected_language")); %>
<% String selectedCountry = ((String) request.getSession().getAttribute("selected_country")); %>
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
                    <li class="active"><a href="/hw2/sources">Sources</a></li>
                    <li><a href="/hw2/articles">Articles</a></li>
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
                            <select name="category">
                                <option value="any" <%= selectedCategory.equalsIgnoreCase("any") ? "selected" : "" %>>Any</option>
                                <% for(Category c : categories) { %>
                                <option value="<%= c.getName() %>" <%= selectedCategory.equalsIgnoreCase(c.getName()) ? "selected" : "" %>><%= c.getName() %></option>
                                <% } %>
                            </select>
                            <label>Category</label>
                        </div>
                        <div class="input-field col s3">
                            <select name="language">
                                <option value="any" <%= selectedLanguage.equalsIgnoreCase("any") ? "selected" : "" %>>Any</option>
                                <% for(Language l : languages) { %>
                                <option value="<%= l.getCode() %>" <%= selectedLanguage.equalsIgnoreCase(l.getCode()) ? "selected" : "" %>><%= l.getName() %></option>
                                <% } %>
                            </select>
                            <label>Language</label>
                        </div>
                        <div class="input-field col s3">
                            <select name="country">
                                <option value="any" <%= selectedCountry.equalsIgnoreCase("any") ? "selected" : "" %>>Any</option>
                                <% for(Country c : countries) { %>
                                <option value="<%= c.getCode() %>" <%= selectedCountry.equalsIgnoreCase(c.getCode()) ? "selected" : "" %>><%= c.getName() %></option>
                                <% } %>
                            </select>
                            <label>Country</label>
                        </div>
                        <div class="col s3">
                            <button class="btn waves-effect waves-light full" type="submit">Filter</button>
                        </div>
                    </form>
                </div>
            </div>

            <% for(List<Source> r : sources) { %>
                <div class="row">
                <% for(Source c : r) { %>
                    <div class="col s12 m6 l4">
                        <div class="card large hoverable">
                            <!-- Card Content -->
                            <div class="card-image waves-effect waves-block waves-light">
                                <img class="activator" src="<%= c.getUrlsToLogos().get("medium")%>">
                            </div>
                            <div class="card-content">
                                <span class="card-title activator grey-text text-darken-4"><%= c.getName() %><i class="material-icons right">more_vert</i></span>
                                <ul class="collection">
                                    <li class="collection-item"><div>Category<span class="new badge" data-badge-caption="<%= c.getCategory() %>"></span></div></li>
                                    <li class="collection-item"><div>Language<span class="new badge" data-badge-caption="<%= (new Locale(c.getLanguage())).getDisplayLanguage() %>"></span></div></li>
                                    <li class="collection-item"><div>Country<span class="new badge" data-badge-caption="<%= (new Locale("", c.getCountry())).getDisplayCountry() %>"></span></div></li>
                                </ul>
                            </div>
                            <div class="card-reveal">
                                <span class="card-title grey-text text-darken-4"><%= c.getName() %><i class="material-icons right">close</i></span>
                                <p><%= c.getDescription() %></p>
                                <a href="<%= c.getUrl() %>" class="waves-effect waves-light btn">Go To Site</a>
                            </div>
                            <div class="card-action">
                                <% for(String s : c.getSortBysAvailable()) { %>
                                    <a href="/hw2/articles?source=<%= c.getId() %>&sortby=<%= s %>" class="waves-effect waves-light btn"><%= s %></a>
                                <% } %>
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
    </script>
    </body>
</html>