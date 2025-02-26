<%@ page session="true" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manage Users</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <script src="commonFunctions.js"></script>

    <style>
        .container {
            margin-top: 20px;
        }
    </style>
    <script>
        window.onload = function (){
            loadUsers();
        };
    </script>
</head>
<body>
    <script>
        

        function editUser(id, username, email, role) {
            alert(`Edit User: ${username} (ID: ${id})`);
            // You can implement a modal form to edit user details here
        };

        function deleteUser(id) {
            if (confirm("Are you sure you want to delete this user?")) {
                fetch(`http://localhost:8080/MegaCityCab/api/users/${id}`, {
                    method: "DELETE",
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Failed to delete user");
                    }
                    return response.text();
                })
                .then(() => {
                    alert("User deleted successfully!");
                    loadUsers(); // Reload user list
                })
                .catch(error => {
                    console.error("Error deleting user:", error);
                    alert("Failed to delete user.");
                });
            }
        };
    </script>
    
    <div class="container">
        <h2 class="mb-4">Manage Users</h2>

        <!-- Users Table -->
        <table class="table table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Telephone</th>
                    <th>Address</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody id="userTableBody">
                <tr><td colspan="5" class="text-center">Loading users...</td></tr>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    

</body>
</html>
