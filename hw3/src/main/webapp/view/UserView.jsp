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
                    <li class="active"><a href="/hw3/">User</a></li>
                    <li><a href="/hw3/sources">Sources</a></li>
                    <li><a href="/hw3/articles">Articles</a></li>
                    <li><a href="/hw3/recommendation">Recommendation</a></li>
                    <li><a href="/hw3/preference">Preferences</a></li>
                </ul>
            </div>
         </nav>

        <div class="container">
            <div class="row">
                <div class="col s12 m6 l4">
                    <div class="card-panel">
                        <h4>Current User</h4>
                        <form action="" method="" id="form1">
                            <div class="input-field inline full">
                                <input disabled name="username" type="text" class="validate" value="<%= request.getSession().getAttribute("userName") %>">
                                <label for="username">User Name</label>
                            </div>
                            <div class="input-field inline full">
                                <input disabled name="apiKey" type="text" class="validate" value="<%= request.getSession().getAttribute("apiKey") %>">
                                <label for="apiKey">API Key</label>
                            </div>
                        </form>
                        </br>
                        <a id="logoutBtn" class="waves-effect waves-light btn full">Logout</a>
                        <a id="deleteBtn" class="waves-effect waves-light btn full">Delete</a>
                    </div>
                </div>
                <div class="col s12 m6 l4">
                    <div class="card-panel">
                        <h4>Change Info</h4>
                        <form action="" method="post" id="form2">
                            <div class="input-field inline full">
                                <input id="changeName" name="username" type="text" class="validate">
                                <label for="username">New User Name</label>
                            </div>
                            <div class="input-field inline full">
                                <input id="changePass" name="password" type="text" class="validate">
                                <label for="password">New Password</label>
                            </div>
                        </form>
                        </br>
                        <a id="submitBtn" class="waves-effect waves-light btn full">Submit</a>
                    </div>
                </div>
            <div class="col s12 m6 l4">
                <div class="card-panel">
                    <h4>Login/Register</h4>
                    <form action="" method="get" id="form3">
                        <div class="input-field inline full">
                            <input id="loginName" name="userName" type="text" class="validate">
                            <label for="userName">User Name</label>
                        </div>
                            <div class="input-field inline full">
                            <input id="loginPass" name="password" type="text" class="validate">
                        <label for="password">Password</label>
                        </div>

                    </form>
                    </br>
                    <a id="loginBtn" class="waves-effect waves-light btn full">Login</a>
                    <a id="registerBtn" class="waves-effect waves-light btn full">Register</a>
                </div>
            </div>
            </div>
        </div>

        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.1/js/materialize.min.js"></script>
		<script>
		    $('#loginBtn').click(function() {
                $.ajax({
                    url: '/hw3/',
                    type: 'GET',
                    data: {'userName':$('#loginName').val(),'password':$('#loginPass').val()},
                    success: function (result) {
                        location.reload();
                    },
                    error: function (xhr, textStatus, error) {
                        Materialize.toast(xhr.status + ": " + xhr.responseText, 4000);
                    }
                });
             });

            $('#logoutBtn').click(function() {
				$.ajax({
					url: '/hw3/',
					type: 'POST',
					data: {'logout':'true'},
					success: function (result) {
					    location.reload();
					},
					error: function (xhr, textStatus, error) {
                        Materialize.toast(xhr.status + ": " + xhr.responseText, 4000);
                    }
				});
			});

			$('#registerBtn').click(function() {
                $.ajax({
                    url: '/hw3/',
                    type: 'PUT',
                    data: { 'userName':$('#loginName').val(),'password': $('#loginPass').val() },
                    success: function (result) {
                        location.reload();
                    },
                    error: function (xhr, textStatus, error) {
                        Materialize.toast(xhr.status + ": " + xhr.responseText, 4000);
                    }
                });
            });

			$('#deleteBtn').click(function() {
                $.ajax({
                    url: '/hw3/',
                    type: 'DELETE',
                    success: function (result) {
                        location.reload();
                    },
                    error: function (xhr, textStatus, error) {
                        Materialize.toast(xhr.status + ": " + xhr.responseText, 4000);
                    }
                });
             });

            $('#submitBtn').click(function() {
                $.ajax({
                    url: '/hw3/',
                    type: 'POST',
                    data: {'userName':$('#changeName').val(),'password':$('#changePass').val()},
                    success: function (result) {
                        location.reload();
                    },
                    error: function (xhr, textStatus, error) {
                        Materialize.toast(xhr.status + ": " + xhr.responseText, 4000);
                    }
                });
            });
        </script>
    </body>
</html>