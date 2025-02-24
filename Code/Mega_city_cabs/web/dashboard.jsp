<%@ page session="true" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dashboard</title>
    <script src="commonFunctions.js"></script>
    <style>
        #dashboardContent{
            display: none;
        }
    </style>
</head>
<body id="dashboardcontent">
    <h1>Welcome, <span id="username"></span></h1>
    <h1>Role, <span id="role"></span></h1>
    <h1>ID, <span id="uid"></span></h1>
    <button onclick="logout()">Logout</button>

    <script>
        var username = '<%= session.getAttribute("username") != null ? session.getAttribute("username") : "" %>';
        var uid = '<%= session.getAttribute("uid") != null ? session.getAttribute("uid") : "" %>';
        var role = '<%= session.getAttribute("role") != null ? session.getAttribute("role") : "" %>';

        window.onload = function () {
            checkSession();
            
        };
    </script>
</body>
</html>
