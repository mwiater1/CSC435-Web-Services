<!DOCTYPE html>
<html lang="en">
<head>
    <#include "header.ftl"/>
</head>
<body>
<#include "navBar.ftl"/>
<div class="container">
    <div class="row">
        <div class="col s12 m6 l4">
            <div class="card-panel">
                <h4>Current User</h4>
                <form action="" method="" id="form1">
                    <div class="input-field inline full">
                        <input id="userName" disabled name="username" type="text" class="validate" value="${userName}">
                        <label for="username">User Name</label>
                    </div>
                    <div class="input-field inline full">
                        <input id="apiKey" disabled name="apiKey" type="text" class="validate" value="${apiKey}">
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
                        <label for="changeName">New User Name</label>
                    </div>
                    <div class="input-field inline full">
                        <input id="changePass" name="password" type="text" class="validate">
                        <label for="changePass">New Password</label>
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
                        <label for="loginName">User Name</label>
                    </div>
                    <div class="input-field inline full">
                        <input id="loginPass" name="password" type="text" class="validate">
                        <label for="loginPass">Password</label>
                    </div>

                </form>
                </br>
                <a id="loginBtn" class="waves-effect waves-light btn full">Login</a>
                <a id="registerBtn" class="waves-effect waves-light btn full">Register</a>
            </div>
        </div>
    </div>
</div>
<#include "scripts.ftl"/>
<script>
    $('#loginBtn').click(function() {
        $.ajax({
            url: '/hw7',
            type: 'GET',
            contentType: 'application/x-www-form-urlencoded',
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
            url: '/hw7',
            type: 'POST',
            data: {'logout':'true'},
            contentType: 'application/x-www-form-urlencoded',
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
            url: '/hw7',
            type: 'PUT',
            contentType: 'application/x-www-form-urlencoded',
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
            url: '/hw7',
            type: 'DELETE',
            contentType: 'application/x-www-form-urlencoded',
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
            url: '/hw7',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded',
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