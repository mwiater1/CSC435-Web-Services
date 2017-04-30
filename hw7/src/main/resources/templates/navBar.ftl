<nav>
    <div class="nav-wrapper">
        <a href="#" class="brand-logo right">Newsly</a>
        <ul class="left">
            <li class="${ (currentPage == "user")?then("active","")}"><a href="/hw7">User</a></li>
            <li class="${ (currentPage == "sources")?then("active","")}"><a href="/hw7/sources">Sources</a></li>
            <li class="${ (currentPage == "articles")?then("active","")}"><a href="/hw7/articles">Articles</a></li>
            <li class="${ (currentPage == "recommend")?then("active","")}"><a href="/hw7/recommend">Recommendation</a></li>
            <li class="${ (currentPage == "preferences")?then("active","")}"><a href="/hw7/preference">Preferences</a></li>
        </ul>
    </div>
</nav>