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
        <form action="/hw7/sources" method="get">
            <div class="input-field col s3">
                <select name="category">
                    <option value="any" ${ (selectedCategory == "any")?then("selected","") }>Any</option>
                    <#list categories as c>
                    <option value="${c.name}" ${ (selectedCategory == c.name)?then("selected","") }>${c.name}</option>
                    </#list>
                </select>
                <label>Category</label>
            </div>
            <div class="input-field col s3">
                <select name="language">
                    <option value="any" ${ (selectedLanguage == "any")?then("selected","") }>Any</option>
                    <#list languages as l>
                    <option value="${l.code}" ${ (selectedLanguage == l.code)?then("selected","") }>${l.name}</option>
                    </#list>
                </select>
                <label>Language</label>
            </div>
            <div class="input-field col s3">
                <select name="country">
                    <option value="any" ${ (selectedCountry == "any")?then("selected","") }>Any</option>
                    <#list countries as c>
                    <option value="${c.code}" ${ (selectedCountry == c.code)?then("selected","") }>${c.name}</option>
                    </#list>
                </select>
                <label>Country</label>
            </div>
            <div class="col s3">
                <button class="btn waves-effect waves-light full" type="submit">Filter</button>
            </div>
        </form>
    </div>
</div>
<#include "sourceList.ftl"/>
</div>
<#include "scripts.ftl"/>
<script>
    $(document).ready(function() {
        $('select').material_select();
    });
</script>
</body>
</html>