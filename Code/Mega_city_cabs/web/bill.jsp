<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trip Details</title>
    
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="triphistory.js"></script>
    <script>
    window.onload = function () {
        let role = <%= session.getAttribute("role") %>;
        alert("User Role: " + role);
        
    };
</script>  
</head>
<body class="container mt-4">

    <h2 class="text-center mb-4">Billing Details</h2>

    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>Bill No</th>
                    <th>Date</th>
                    <th>Driver</th>
                    <th>Vehicle</th>
                    <th>Pickup</th>
                    <th>Drop</th>
                    <th>Distance (km)</th>
                    <th>Fare</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody id="bookingTableBody">
                
            </tbody>
        </table>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
