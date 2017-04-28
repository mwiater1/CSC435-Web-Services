<!DOCTYPE html>
<html lang="en">
<head>
    <title>Newsly</title>
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import Google Roboto Font-->
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.1/css/materialize.min.css"  media="screen,projection"/>
    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <!--Import jQuery before materialize.js-->
    <style>
    a, i {
        cursor: pointer;
    }
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
            <li class="${ params.controller?.equalsIgnoreCase("user") ? "active" : ""}"><a href="/hw6">User</a></li>
            <li class="${ params.controller?.equalsIgnoreCase("source") ? "active" : ""}"><a href="/hw6/sources">Sources</a></li>
            <li class="${ params.controller?.equalsIgnoreCase("article") ? "active" : ""}"><a href="/hw6/articles">Articles</a></li>
            <li class="${ params.controller?.equalsIgnoreCase("recommendation") ? "active" : ""}"><a href="/hw6/recommendation">Recommendation</a></li>
            <li class="${ params.controller?.equalsIgnoreCase("preference") ? "active" : ""}"><a href="/hw6/preference">Preferences</a></li>
        </ul>
    </div>
</nav>
<div class="container">
    <g:layoutBody/>
</div>
<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.1/js/materialize.min.js"></script>
<g:layoutHead/>
</body>
</html>
