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
        </style>
    </head>

    <body>
        <nav>
            <div class="nav-wrapper">
              <a href="#" class="brand-logo center">Newsly</a>
              <ul class="left">
                <li class="active"><a href="#">User</a></li>
                <li><a href="#">Sources</a></li>
                <li><a href="#">Articles</a></li>
                <li><a href="#">Recommendation</a></li>
              </ul>
            </div>
         </nav>

        <div class="container">
            <div class="row">
                <div class="col s4">
                    <div class="card-panel">
                        <h4>Current User</h4>
                        <form action="user" method="" id="form1">
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
                        <a id="logoutBtn" class="waves-effect waves-light btn"><i class="material-icons right">clear</i>Logout</a>
                        <a id="delBtn" class="waves-effect waves-light btn"><i class="material-icons right">block</i>Delete</a>
                    </div>
                </div>
                <div class="col s4">
                    <div class="card-panel">
                        <h4>Change Info</h4>
                        <form action="" method="post" id="form2">
                            <div class="input-field inline full">
                                <input name="username" type="text" class="validate">
                                <label for="username">New User Name</label>
                            </div>
                            <div class="input-field inline full">
                                <input name="password" type="text" class="validate">
                                <label for="password">New Password</label>
                            </div>
                        </form>
                        </br>
                        <button class="btn waves-effect waves-light" type="submit" form="form2">Submit
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            <div class="col s4">
                <div class="card-panel">
                    <h4>Login/Register</h4>
                    <form action="" method="get" id="form3">
                        <div class="input-field inline full">
                            <input id="userVal" name="userName" type="text" class="validate">
                            <label for="userName">User Name</label>
                        </div>
                            <div class="input-field inline full">
                            <input id="passVal" name="password" type="text" class="validate">
                        <label for="password">Password</label>
                        </div>
                    </form>
                    </br>
                    <button class="btn waves-effect waves-light" type="submit" form="form3">Login
                        <i class="material-icons right">send</i>
                    </button>
                    <a id="registerBtn" class="waves-effect waves-light btn"><i class="material-icons right">block</i>Delete</a>
                </div>
            </div>
            </div>
        </div>

        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.1/js/materialize.min.js"></script>
		<script>
            $('#delBtn').click(function() {
				$.ajax({
					url: '',
					type: 'DELETE',
					data: {'submit':true},
					success: function (result) {
                        alert("Your bookmark has been saved");
					}
				});
			});

            $('#registerBtn').click(function() {
                $.ajax({
                    url: '',
                    type: 'DELETE',
                    data: {'submit':true},
                    success: function (result) {
                        alert("Your bookmark has been saved");
                    }
                });
            });

            $('#logoutBtn').click(function() {
                $.ajax({
                    url: '',
                    type: 'DELETE',
                    data: {'logout':'true'},
                    success: function (result) {
                        location.reload();
                    }
                });
            });
        </script>
    </body>
</html>