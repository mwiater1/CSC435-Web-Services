<!DOCTYPE html>
<html lang="en">
<head>
    <script>
        $(document).ready(function() {
            $('select').material_select();
        });
    </script>
</head>
<body>
<div class="row card">
    <div class="card-content">
        <form action="/hw6/sources" method="get">
            <div class="input-field col s3">
                <select name="category">
                    <option value="any" ${ selectedCategory?.equalsIgnoreCase("any") ? "selected" : "" }>Any</option>
                    <g:each in="${categories}" var="c">
                    <option value="${c.name}" ${ selectedCategory?.equalsIgnoreCase(c.name) ? "selected" : "" }>${c.name}</option>
                    </g:each>
                </select>
                <label>Category</label>
            </div>
            <div class="input-field col s3">
                <select name="language">
                    <option value="any" ${ selectedLanguage?.equalsIgnoreCase("any") ? "selected" : "" }>Any</option>
                    <g:each in="${languages}" var="l">
                    <option value="${l.code}" ${ selectedLanguage?.equalsIgnoreCase(l.code) ? "selected" : "" }>${l.name}</option>
                    </g:each>
                </select>
                <label>Language</label>
            </div>
            <div class="input-field col s3">
                <select name="country">
                    <option value="any" ${ selectedCountry?.equalsIgnoreCase("any") ? "selected" : "" }>Any</option>
                    <g:each in="${countries}" var="c">
                    <option value="${c.code}" ${ selectedCountry?.equalsIgnoreCase(c.code) ? "selected" : "" }>${c.name}</option>
                    </g:each>
                </select>
                <label>Country</label>
            </div>
            <div class="col s3">
                <button class="btn waves-effect waves-light full" type="submit">Filter</button>
            </div>
        </form>
    </div>
</div>
<g:render template="/sourceList" />

</body>
</html>