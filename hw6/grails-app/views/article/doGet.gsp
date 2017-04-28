<!DOCTYPE html>
<html lang="en">
<head>
    <script>
        $(document).ready(function() {
            $('select').material_select();
        });

        $('.favorite-star').click(function () {
            var obj = $(this);
            var articleId = obj.data('articleid');
            if($(this).html() === 'star') {
                $.ajax({
                    url: '/hw6/preference',
                    type: 'POST',
                    contentType: 'application/x-www-form-urlencoded',
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
                    url: '/hw6/preference',
                    type: 'POST',
                    contentType: 'application/x-www-form-urlencoded',
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
                    url: '/hw6/preference',
                    type: 'POST',
                    contentType: 'application/x-www-form-urlencoded',
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
                    url: '/hw6/preference',
                    type: 'POST',
                    contentType: 'application/x-www-form-urlencoded',
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
</head>
<body>
<div class="row card">
    <div class="card-content">
        <form action="/hw6/articles" method="get">
            <div class="input-field col s3">
                <select name="source">
                    <option value="any" ${ selectedSource?.equalsIgnoreCase("any") ? "selected" : "" }>Any</option>
                    <g:each in="${sources}" var="s">
                    <option value="${s.id}" ${ selectedSource?.equalsIgnoreCase(s.id) ? "selected" : "" }>${s.name}</option>
                    </g:each>
                </select>
                <label>Source</label>
            </div>
            <div class="input-field col s3">
                <select name="sortby">
                    <option value="any" ${ selectedSortBy?.equalsIgnoreCase("any") ? "selected" : "" }>Any</option>
                    <g:each in="${sortBys}" var="s">
                    <option value="${s}" ${ selectedSortBy?.equalsIgnoreCase(s) ? "selected" : "" }>${s}</option>
                    </g:each>
                </select>
                <label>SortBy</label>
            </div>
            <div class="input-field col s3">
                <select name="category">
                    <option value="any" ${ selectedCategory?.equalsIgnoreCase("any") ? "selected" : "" }>Any</option>
                    <g:each in="${categories}" var="c">
                    <option value="${c.name}" ${ selectedCategory?.equalsIgnoreCase(c.name) ? "selected" : "" }>${c.name}</option>
                    </g:each>
                </select>
                <label>Category</label>
            </div>
            <div class="col s3">
                <button class="btn waves-effect waves-light full" type="submit">Filter</button>
            </div>
        </form>
    </div>
</div>

<g:render template="/articleList"/>

</body>
</html>