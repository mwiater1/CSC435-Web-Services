<div class="row">
    <g:each in="${articles}" var="a">
    <div class="col s12 m6 l4">
        <div class="card large hoverable">
            <!-- Card Content -->
            <div class="card-image waves-effect waves-block waves-light">
                <img class="activator" src="${a.urlToImage}">
            </div>
            <div class="card-content">
                <span class="activator grey-text text-darken-4">${a.title}<i class="material-icons right">more_vert</i></span>
            </div>
            <div class="card-reveal">
                <span class="card-title grey-text text-darken-4">${a.title}<i class="material-icons right">close</i></span>
                <p>${a.description}</p>
                <ul class="collection">
                    <li class="collection-item"><div>Source<span class="new badge" data-badge-caption="${a.sourceId}"></span></div></li>
                    <li class="collection-item"><div>SortBy<span class="new badge" data-badge-caption="${a.sortBy}"></span></div></li>
                    <li class="collection-item"><div>Published<span class="new badge" data-badge-caption="${a.publishedAt}"></span></div></li>
                    <li class="collection-item"><div>Author<span class="new badge" data-badge-caption="${a.author}"></span></div></li>
                </ul>
            </div>
            <div class="card-action">
                <a href="${a.url}" class="waves-effect waves-light btn">Go To Article</a>
                <a>
                    <i class="material-icons small favorite-star right" data-articleid="${a.id}">${ preferenceMap.containsKey(a.id) && preferenceMap.get(a.id).favorite ? "star" : "star_border" }</i>
                </a>
                <a>
                    <i class="material-icons small read right" data-articleid="${a.id}">${ preferenceMap.containsKey(a.id) && preferenceMap.get(a.id).read ? "drafts" : "markunread"}</i>
                </a>
            </div>
        </div>
    </div>
    </g:each>
</div>