<!DOCTYPE html>
<html lang="en">
<head>
    <#include "header.ftl"/>
</head>
<body>
<#include "navBar.ftl"/>
<div class="container">
    <div class="row card">
        <div class="card-content">
            <form action="/hw7/preference" method="get">
                <div class="col s4">
                    <p>
                        <input type="checkbox" id="favorite" name="favorite" value="true" ${ favorite?then("checked","") }/>
                        <label for="favorite">Favorite?</label>
                    </p>
                </div>
                <div class="col s4">
                    <p>
                        <input type="checkbox" id="read" name="read" value="true" ${ read?then("checked","") }/>
                        <label for="read">Read?</label>
                    </p>
                </div>
                <div class="col s4">
                    <button class="btn waves-effect waves-light full" type="submit">Filter</button>
                </div>
            </form>
        </div>
    </div>
    <#include "articleList.ftl"/>
</div>
<#include "scripts.ftl"/>
<script>
    $(document).ready(function() {
        $('select').material_select();
    });

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
                    location.reload();
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
                url: '/hw7/preference',
                type: 'POST',
                contentType: 'application/x-www-form-urlencoded',
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
                url: '/hw7/preference',
                type: 'POST',
                contentType: 'application/x-www-form-urlencoded',
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