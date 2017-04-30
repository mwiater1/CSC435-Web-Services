<!DOCTYPE html>
<html lang="en">
<head>
    <#include "header.ftl"/>
</head>
<body>
    <#include "navBar.ftl"/>
    <div class="container">
        <#include "articleList.ftl"/>
    </div>
    <#include "scripts.ftl"/>
    <script>
        $('.favorite-star').click(function () {
            var obj = $(this);
            var articleId = obj.data('articleid');
            if($(this).html() === 'star') {
                $.ajax({
                    url: '/hw7/preference',
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
                    url: '/hw7/preference',
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
                    url: '/hw7/preference',
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
                    url: '/hw7/preference',
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
</body>
</html>