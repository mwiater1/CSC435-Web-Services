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
            <form action="/hw7/articles" method="get">
                <div class="input-field col s3">
                    <select name="source">
                        <option value="any" ${ (selectedSource == "any")?then("selected","") }>Any</option>
                        <#list sources as s>
                        <option value="${s.id}" ${ (selectedSource == s.id)?then("selected","") }>${s.name}</option>
                        </#list>
                    </select>
                    <label>Source</label>
                </div>
                <div class="input-field col s3">
                    <select name="sortby">
                        <option value="any" ${ (selectedSortBy == "any")?then("selected","") }>Any</option>
                        <#list sortBys as s>
                        <option value="${s}" ${ (selectedSortBy == s)?then("selected","") }>${s}</option>
                        </#list>
                    </select>
                    <label>SortBy</label>
                </div>
                <div class="input-field col s3">
                    <select name="category">
                        <option value="any" ${ (selectedCategory == "any")?then("selected","") }>Any</option>
                        <#list categories as c>
                        <option value="${c.name}" ${ (selectedCategory == c.name)?then("selected","") }>${c.name}</option>
                        </#list>
                    </select>
                    <label>Category</label>
                </div>
                <div class="col s3">
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