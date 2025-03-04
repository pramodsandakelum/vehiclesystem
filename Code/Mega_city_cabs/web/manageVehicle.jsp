<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Vehicles</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2>Manage Vehicles</h2>

    <!-- 🚗 Vehicle Form -->
    <form id="vehicleForm">
        <input type="hidden" id="vid"> <!-- Hidden for Update -->
        
        <label class="form-label">Vehicle Type:</label>
        <select id="type" class="form-control">
            <option value="Car">Car</option>
            <option value="Van">Van</option>
        </select>

        <label class="form-label">Vehicle Number:</label>
        <input type="text" id="number" class="form-control" required>

        <button type="submit" class="btn btn-primary mt-3">Save Vehicle</button>
    </form>

    <!-- 🚗 Vehicle Table -->
    <table class="table mt-4">
        <thead>
            <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Number</th>
                <th>Booked</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="vehicleTableBody">
            <!-- Dynamic Data -->
        </tbody>
    </table>
</div>

<!-- 🔹 Edit Modal -->
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

                    <label class="form-label">Booked:</label>
                    <select id="editBooked" class="form-control">
                        <option value="0">No</option>
                        <option value="1">Yes</option>
                    </select>

                    <button type="submit" class="btn btn-success mt-3">Update Vehicle</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="vehicle.js"></script>

</body>
</html>
