<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="icon" type="image/x-icon" href="favicon.ico">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mega City Cabs - Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-image: url('https://images.unsplash.com/photo-1582820795651-f358eebe4406?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');

                background-size: cover;
                background-position: center;
                height: 100vh;
                color: white;
            }
            .login-container {
                background-color: rgba(0, 0, 0, 0.6); 
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
                width: 100%;
                max-width: 400px;
                margin: 0 auto;
            }
            .login-container h2 {
                margin-bottom: 30px;
            }
            .form-label {
                font-size: 1.1rem;
            }
            .btn-primary {
                width: 100%;
                padding: 12px;
                font-size: 1.1rem;
            }
            .text-danger {
                font-size: 0.9rem;
            }
            .footer {
                text-align: center;
                color: #bbb;
                margin-top: 20px;
            }
            .footer a {
                color: #bbb;
            }
            .footer a:hover {
                color: white;
            }
        </style>
    </head>
    <body>
        <div class="container d-flex justify-content-center align-items-center h-100">
            <div class="login-container">
                <h2 class="text-center">Mega City Cabs</h2>
                <form id="loginForm">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" placeholder="Enter your username" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" placeholder="Enter your password" required>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary">Login</button>
                    </div>
                    <div id="errorMessage" class="text-danger mt-2 text-center" style="display: none;">Invalid credentials. Please try again.</div>
                </form>
                <div class="footer mt-4">
                    <p>Don't have an account? <a href="signup.jsp">Sign Up</a></p>
                </div>
            </div>
        </div>

        <script>
            document.getElementById('loginForm').addEventListener('submit', function (e) {
                e.preventDefault();

                const username = document.getElementById('username').value;
                const password = document.getElementById('password').value;

                
                fetch('http://localhost:8080/Mega_city_cabs/api/user/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include',
                    body: JSON.stringify({username, password}) 
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {   
                                window.location.href = 'dashboard.jsp';  
                            } else {                               
                                document.getElementById('errorMessage').style.display = 'block';
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                            document.getElementById('errorMessage').style.display = 'block';
                        });
            });
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
