<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Vehicles</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
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
</head>
<body>

<div class="container mt-4">
    <h2>Manage Vehicles</h2>
    
    <form id="vehicleForm">
        <input type="hidden" id="vid">
        
        <label class="form-label">Vehicle Type:</label>
        <select id="type" class="form-control">
            <option value="Car">Car</option>
            <option value="Van">Van</option>
        </select>

        <label class="form-label">Vehicle Number:</label>
        <input type="text" id="number" class="form-control" required>

        <button onclick="saveVehicle()"class="btn btn-primary mt-3">Save Vehicle</button>
    </form><br>

    <table class="table table-striped">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Number</th>
                <th>Booked</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="vehicleTableBody">
        </tbody>
    </table>
</div>

<div class="modal fade" id="editVehicleModal" tabindex="-1" aria-labelledby="editVehicleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editVehicleModalLabel">Edit Vehicle</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editVehicleForm">
                    <input type="hidden" id="editVid">
                    
                    <label class="form-label">Vehicle Type:</label>
                    <select id="editType" class="form-control">
                        <option value="Car">Car</option>
                        <option value="Van">Van</option>
                    </select>

                    <label class="form-label">Vehicle Number:</label>
                    <input type="text" id="editNumber" class="form-control" required>


                    <button onclick="updateVehicle()" class="btn btn-success mt-3">Update Vehicle</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="vehicle.js"></script>

</body>
</html>
