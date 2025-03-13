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
        password: password, 
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

function loadUsers() {
    
    fetch("http://localhost:8080/Mega_city_cabs/api/user/getAllUsers")
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
    let isDisabled = user.role === 2 && user.booked === true; // Check role and booking status
    let rowClass = user.booked ? "table-danger" : ""; // Apply red background if booked

    rows += `
        <tr class="${rowClass}">
            <td>${user.uid}</td>
            <td>${user.username}</td>
            <td style='display:none;'>${user.password}</td>
            <td>${user.fname}</td>
            <td>${user.lname}</td>
            <td>${user.email}</td>
            <td>${user.telephone}</td>
            <td style='display:none;'>${user.address}</td>
            <td>${getRoleText(user.role)}</td>
            <td>
                <button class="btn btn-primary btn-sm" 
                        onclick='openUserModal({
                            uid: ${user.uid},
                            username: "${user.username}",
                            password: "${user.password}",
                            fname: "${user.fname}",
                            lname: "${user.lname}",
                            email: "${user.email}",
                            telephone: "${user.telephone}",
                            address: "${user.address}",
                            role: ${user.role}
                        })'
                        ${isDisabled ? "disabled" : ""}>
                    <i class="fas fa-edit"></i> Edit
                </button>
                <button class="btn btn-danger btn-sm" 
                        onclick="deleteUser(${user.uid})"
                        ${isDisabled ? "disabled" : ""}>
                    <i class="fas fa-trash"></i> Delete
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
            return "Unknown Role";
    }
}

let isEdit = false;

function openUserModal(user = null) {
    let isEdit = user !== null;
    document.getElementById("userModalLabel").textContent = isEdit ? "Edit User" : "Add User";

    document.getElementById("userId").value = isEdit ? user.uid : "";
    document.getElementById("username").value = isEdit ? user.username : "";
    document.getElementById("password").value = isEdit ? user.password : "";
    document.getElementById("firstName").value = isEdit ? user.fname : "";
    document.getElementById("lastName").value = isEdit ? user.lname : "";
    document.getElementById("email").value = isEdit ? user.email : "";
    document.getElementById("telephone").value = isEdit ? user.telephone : "";
    document.getElementById("address").value = isEdit ? user.address : "";
    document.getElementById("role").value = isEdit ? user.role : "3";

    let modal = new bootstrap.Modal(document.getElementById("userModal"));
    modal.show();
}



function saveUser() {
    let isEdit = document.getElementById("userId").value !== ""; // Determine if editing or creating new user

    
    let user = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        fname: document.getElementById("firstName").value,
        lname: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        telephone: document.getElementById("telephone").value,
        address: document.getElementById("address").value,
        role: parseInt(document.getElementById("role").value)
    };
    
    

    if (isEdit) {
        user.uid = parseInt(document.getElementById("userId").value);
    }

    let url = isEdit
        ? "http://localhost:8080/Mega_city_cabs/api/user/updateAccount"
        : "http://localhost:8080/Mega_city_cabs/api/user/createAccount";
    
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
    .then(response => response.json())
    .then(data => {
        if (data.message === "Account Created Successfully") {
            alert("✅ Account created successfully!");
                    loadUsers();
                    let modal = new bootstrap.Modal(document.getElementById("userModal"));
                    
            //window.location.href = "index.jsp";
        } else if(data.message === "Account Updated Successfully") {
            alert("✅ Account updated successfully!");
                    loadUsers();
                    let modal = new bootstrap.Modal(document.getElementById("userModal"));
                    
        }else{
            alert("❌ Error: " + (data.error || "Unknown error"));
        }
    })
    .catch(error => {
        console.error("Error creating account:", error);
        alert("❌ Network error. Please try again.");
    });
}



// Function to delete a user
function deleteUser(id) {
    if (confirm("Are you sure you want to delete this user?")) {
        fetch(`http://localhost:8080/Mega_city_cabs/api/user/deleteAccount/${id}`)
        .then(response => response.json())
    .then(data => {
        if (data.message === "Account Deleted Successfully") {
            alert("✅ Account Deleted successfully!");
                    loadUsers();
            
        } else {
            alert("❌ Error: " + (data.error || "Unknown error"));
        }
    })
    .catch(error => {
        console.error("Error creating account:", error);
        alert("❌ Network error. Please try again.");
    });
}
}



