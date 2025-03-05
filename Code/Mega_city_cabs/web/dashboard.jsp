<%@ page session="true" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="commonFunctions.js"></script>

    <style>
        body {
            display: flex;
        }
        .sidebar {
            width: 280px;
            height: 100vh;
            background-color: #343a40;
            color: white;
            position: fixed;
            padding-top: 20px;
            transition: all 0.3s ease;
        }
        .sidebar .profile {
            text-align: center;
            padding: 15px;
            border-bottom: 1px solid #495057;
        }
        .sidebar .profile img {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            border: 2px solid white;
        }
        .sidebar .profile h5 {
            margin-top: 10px;
            font-size: 18px;
        }
        .sidebar .profile p {
            font-size: 14px;
            color: #adb5bd;
        }
        .sidebar a {
            display: block;
            color: white;
            padding: 12px;
            text-decoration: none;
            font-size: 16px;
            border-radius: 5px;
            margin: 5px 10px;
        }
        .sidebar a:hover {
            background-color: #495057;
        }
        .sidebar .logout {
            position: absolute;
            bottom: 20px;
            width: 100%;
            text-align: center;
        }
        .content {
            margin-left: 280px;
            padding: 20px;
            width: 100%;
        }
        iframe {
            width: 100%;
            height: 90vh;
            border: none;
        }
    </style>
</head>
<body>

    <!-- Sidebar -->
    <div class="sidebar">
        <div class="profile">
            <img src="user-avatar.png" alt="User Avatar"> <!-- Replace with dynamic avatar -->
            <h5 id="username">Loading...</h5>
            <p id="role-text">Loading...</p>
        </div>

        

        <% Integer role = (Integer) session.getAttribute("role"); %>

        <% if (role != null && role == 1) { %> <!-- Admin -->
            <a href="#" onclick="loadPage('manageUsers.jsp')">Manage Users</a>
            <a href="#" onclick="loadPage('manageVehicle.jsp')">Manage Vehicles</a>
        <% } %>

        <% if (role != null && (role == 1 || role == 2)) { %> <!-- Admin & Driver -->
            <a href="#" onclick="loadPage('driverSchedule.jsp')">Driver Schedule</a>
        <% } %>

        <% if (role != null && (role == 1 || role == 3)) { %> <!-- Admin & Customer -->
            <a href="#" onclick="loadPage('bookride.jsp')">Book a Ride</a>
            <a href="#" onclick="loadPage('rideHistory.jsp')">Ride History</a>
        <% } %>

        <div class="logout">
            <a href="#" class="text-danger" onclick="logout()">Logout</a>
        </div>
    </div>

    <!-- Main Content Area -->
    <div class="content">
        <iframe id="contentFrame" src=""></iframe>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        var username = '<%= session.getAttribute("username") != null ? session.getAttribute("username") : "" %>';
        var role = '<%= session.getAttribute("role") != null ? session.getAttribute("role").toString() : "" %>';

        document.getElementById("username").textContent = username;
        
        if (role === "1") {
            document.getElementById("role-text").textContent = "Admin";
        } else if (role === "2") {
            document.getElementById("role-text").textContent = "Driver";
        } else if (role === "3") {
            document.getElementById("role-text").textContent = "Customer";
        } else {
            document.getElementById("role-text").textContent = "Unknown Role";
        }

        function loadPage(page) {
            document.getElementById("contentFrame").src = page;
        }

        
    </script>

</body>
</html>
