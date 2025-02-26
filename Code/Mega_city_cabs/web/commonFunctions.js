function checkSession() {
    //alert("checksession");
    fetch("http://localhost:8080/Mega_city_cabs/api/user/sessionCheck?" + new Date().getTime(), {// Append timestamp to prevent caching
        method: "GET",
        credentials: "include",
        headers: {
            "Cache-Control": "no-cache, no-store, must-revalidate",
            "Pragma": "no-cache",
            "Expires": "0"
        }
    })
            .then(response => {
                if (response.status === 401) { // Redirect to login if session is invalid
                    window.location.replace("index.jsp");

                }
                return response.json();
            })
            .then(data => {
                if (!data.authenticated) {
                    window.location.replace("index.jsp");

                } else {
                    document.getElementById("dashboardContent").style.display = "block";
                }
            })
            .catch(error => console.error("Error checking session:", error));
}
// Logout function to clear session and redirect
function logout() {
    fetch("http://localhost:8080/Mega_city_cabs/api/user/logout", {
        method: "POST",
        credentials: "include" // Ensures cookies are sent with the request
    })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Clear session storage and redirect to login
                    sessionStorage.removeItem("uid");
                    sessionStorage.removeItem("username");
                    sessionStorage.removeItem("role");
                    sessionStorage.clear();
                    document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
                    window.location.replace("index.jsp");
                    setTimeout(() => {
                        window.history.pushState(null, null, "index.jsp");
                    }, 100);
                }
            })
            .catch(error => console.error("Error logging out:", error));
}

function createAccount() {
    // Get input values
    let username = document.getElementById("username").value.trim();
    let password = document.getElementById("password").value;
    let email = document.getElementById("email").value.trim();
    let role = document.getElementById("role").value;
    let fname = document.getElementById("fname").value.trim();
    let lname = document.getElementById("lname").value.trim();
    let telephone = document.getElementById("telephone").value.trim();
    let address = document.getElementById("address").value.trim();

    // Basic validation
    if (!username || !password || !email || !role || !fname || !lname || !telephone || !address) {
        alert("All fields are required!");
        return;
    }

    // Validate email format
    if (!/^\S+@\S+\.\S+$/.test(email)) {
        alert("Invalid email format!");
        return;
    }

    // Validate telephone format (basic check)
    if (!/^\d{10}$/.test(telephone)) {
        alert("Invalid phone number! Must be 10 digits.");
        return;
    }

    // Prepare user data
    let User = {
        username: username,
        password: password, // Ensure the backend hashes this!
        email: email,
        role: parseInt(role),
        fname: fname,
        lname: lname,
        telephone: telephone,
        address: address
    };
    console.log(User);
    // Send data to backend
    fetch("http://localhost:8080/Mega_city_cabs/api/user/createAccount", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(User)
    })
            .then(response => response.json())
            .then(data => {
                if (data.message == "Account Created Successfully") {
                    alert("✅ Account created successfully!");
                    //window.location.href = "index.jsp"; // Redirect to login page
                } else {
                    alert("❌ Error: " + (data.error || "Unknown error"));
                }
            })
            .catch(error => {
                console.error("Error creating account:", error);
                alert("❌ Network error. Please try again.");
            });
}


//for admin manageusers
function loadUsers() {
    alert('test');
    fetch("http://localhost:8080/Mega_city_cabs/api/user/getAllUsers") // Update with your actual API endpoint
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json(); // Convert response to JSON
            })
            .then(data => {
                console.log(data);
                let rows = "";
                data.forEach(user => {
                    rows += `
                            <tr>
                                <td>${user.uid}</td>
                                <td>${user.username}</td>
                                <td>${user.password}</td>
                                <td>${user.fname}</td>
                                <td>${user.lname}</td>
                                <td>${user.email}</td>
                                <td>${user.telephone}</td>
                                <td>${user.address}</td>
                                <td>${getRoleText(user.role)}</td>
                                <td>
                                <button class="btn btn-sm btn-warning me-2" onclick="updateUser(${user.id}, '${user.username}', '${user.email}', ${user.role})">
                                <i class="fas fa-edit"></i> <!-- FontAwesome Edit Icon -->
                                </button>
                                <button class="btn btn-sm btn-danger" onclick="deleteUser(${user.id})">
                                <i class="fas fa-trash-alt"></i> <!-- FontAwesome Delete Icon -->
                                </button>
                                </td>
                            </tr>
                        `;
                });
                document.getElementById("userTableBody").innerHTML = rows;
            })
            .catch(error => {
                console.error("Error fetching users:", error);
                document.getElementById("userTableBody").innerHTML = `<tr><td colspan="5" class="text-center text-danger">Error loading users</td></tr>`;
            });
}

function getRoleText(role) {
    switch (role) {
        case 1:
            return "Admin";
        case 2:
            return "Driver";
        case 3:
            return "Customer";
        default:
            return "Unknown Role"; // Handle unexpected values
    }
}



