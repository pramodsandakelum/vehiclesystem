function loginCheck(uid, username,role) {
    if (uid == null | uid == '') {
        // Redirect to login page if UID is not found
        window.location.href = 'http://localhost:8080/Mega_city_cabs/index.jsp';
    } else {
        document.getElementById('username').textContent = username;
        document.getElementById('uid').textContent = uid;
        document.getElementById('role').textContent = role;
    }
}

// Logout function to clear session and redirect
function logout() {
    fetch('http://localhost:8080/Mega_city_cabs/api/user/logout', {
        method: 'POST',
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => {
        // Clear cookies on logout
        document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/";
        window.location.href = 'http://localhost:8080/Mega_city_cabs/index.jsp';
    })
    .catch(error => {
        console.error('Logout failed:', error);
    });
}
