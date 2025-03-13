<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trip Details</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="triphistory.js"></script>
    <script src="commonFunctions.js"></script>
        <script>
            let inactivityTimer;
            function resetInactivityTimer() {        
                console.log("reset");
                clearTimeout(inactivityTimer);
                inactivityTimer = setTimeout(function () {
                    if (window.parent && window.parent.autoLogout) {
                        window.parent.autoLogout();
                    } else {
                        console.error("autoLogout function not found in parent window.");
                    }
                }, 900000); // 15 mins logout
            }

            window.onload = resetInactivityTimer;
            document.onmousemove = resetInactivityTimer;
            document.onkeypress = resetInactivityTimer;
            document.onclick = resetInactivityTimer;
        </script>
    <script>
    window.onload = function () {
        let role = <%= session.getAttribute("role") %>;
        let uid = <%= session.getAttribute("uid") %>;
        //alert(uid);
        fetchBookings(role,uid);
    };
</script>  
</head>
<body class="container mt-4">

    <h2 class="text-center mb-4">Trip Details</h2>

    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>Code</th>
                    <th>Date</th>
                    <th>Customer</th>
                    <th>Driver</th>
                    <th>Vehicle</th>
                    <th>Pickup</th>
                    <th>Drop</th>
                    <th>Distance (km)</th>
                    <th>Fare</th>
                    <th>Ongoing</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody id="bookingTableBody">
                <!-- Data will be dynamically inserted here -->
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
