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
        <select id="booked" class="form-control">
            <option value="1">Car</option>
            <option value="2">Van</option>
        </select>

        <label class="form-label">Vehicle Number:</label>
        <input type="text" id="number" class="form-control" required>

        <label class="form-label">Booked:</label>
        <select id="booked" class="form-control">
            <option value="false">No</option>
            <option value="true">Yes</option>
        </select>

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

<script>
    // 🌟 Fetch and display all vehicles
    async function loadVehicles() {
        let response = await fetch("api/vehicle/getAll");
        let vehicles = await response.json();

        let tableBody = document.getElementById("vehicleTableBody");
        tableBody.innerHTML = ""; // Clear previous rows

        vehicles.forEach(v => {
            let row = `<tr>
                <td>${v.vid}</td>
                <td>${v.type}</td>
                <td>${v.number}</td>
                <td>${v.booked ? "Yes" : "No"}</td>
                <td>
                    <button class="btn btn-warning btn-sm" onclick="editVehicle(${v.vid}, '${v.type}', '${v.number}', ${v.booked})">Edit</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteVehicle(${v.vid})">Delete</button>
                </td>
            </tr>`;
            tableBody.innerHTML += row;
        });
    }

    // 🚗 Handle Form Submission (Add/Update)
    document.getElementById("vehicleForm").onsubmit = async function(event) {
        event.preventDefault();

        let vid = document.getElementById("vid").value;
        let type = document.getElementById("type").value;
        let number = document.getElementById("number").value;
        let booked = document.getElementById("booked").value === "true";

        let vehicleData = { type, number, booked };

        let url = vid ? `api/vehicle/update/${vid}` : "api/vehicle/add";
        let method = vid ? "PUT" : "POST";

        let response = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(vehicleData)
        });

        let result = await response.text();
        alert(result);
        document.getElementById("vehicleForm").reset();
        document.getElementById("vid").value = ""; // Reset hidden field
        loadVehicles();
    };

    // ✏️ Edit Vehicle
    function editVehicle(id, type, number, booked) {
        document.getElementById("vid").value = id;
        document.getElementById("type").value = type;
        document.getElementById("number").value = number;
        document.getElementById("booked").value = booked;
    }

    // ❌ Delete Vehicle
    async function deleteVehicle(id) {
        if (confirm("Are you sure you want to delete this vehicle?")) {
            let response = await fetch(`api/vehicle/delete/${id}`, { method: "DELETE" });
            let result = await response.text();
            alert(result);
            loadVehicles();
        }
    }

    // 🚀 Load Vehicles on Page Load
    loadVehicles();
</script>

</body>
</html>
