<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book a Ride</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="bookride.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2>Book a Ride</h2>
    <form id="bookingForm">
        <div class="mb-3">
            <label for="cid" class="form-label">Customer ID:</label>
            <input type="number" class="form-control" id="cid" name="cid" required>
        </div>
        
        <div class="mb-3">
            <label for="pickupid" class="form-label">Pickup Location:</label>
            <select class="form-control" id="pickupid" name="pickupid" required>
                <option value="">Select Pickup</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="dropid" class="form-label">Drop Location:</label>
            <select class="form-control" id="dropid" name="dropid" required>
                <option value="">Select Drop</option>
            </select>
        </div>
        
        <div class="mb-3">
            <label for="distance" class="form-label">Total Distance</label>
            <input type="number" class="form-control" id="cid" name="cid" disabled>
        </div>
        
        <div class="mb-3">
            <label for="price" class="form-label">Price</label>
            <input type="number" class="form-control" id="cid" name="cid" disabled>
        </div>
        
        <div class="mb-3">
            <button class="btn btn-success" onclick="calculateFare()">Calculate</button>
        </div>
       

        <div class="mb-3">
            <label for="vid" class="form-label">Vehicle:</label>
            <select class="form-control" id="vid" name="vid" required>
                <option value="">Select Vehicle</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="did" class="form-label">Driver:</label>
            <select class="form-control" id="did" name="did" required>
                <option value="">Select Driver</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Book Ride</button>
    </form>

    <div id="message" class="mt-3"></div>
</div>

<script>
    // Fetch locations for pickup and drop-downs
    

    // Load drivers and vehicles (Dummy data, replace with API call if needed)
    function loadDriversAndVehicles() {
        // Example drivers
        let driverSelect = document.getElementById("did");
        ["1", "2", "3"].forEach(id => {
            driverSelect.appendChild(new Option("Driver " + id, id));
        });

        // Example vehicles
        let vehicleSelect = document.getElementById("vid");
        ["101", "102", "103"].forEach(id => {
            vehicleSelect.appendChild(new Option("Vehicle " + id, id));
        });
    }

    document.addEventListener("DOMContentLoaded", function () {
        loadDestinations();
        loadDriversAndVehicles();

        document.getElementById("bookingForm").addEventListener("submit", function (event) {
            event.preventDefault();

            let formData = {
                cid: document.getElementById("cid").value,
                did: document.getElementById("did").value,
                vid: document.getElementById("vid").value,
                pickupid: document.getElementById("pickupid").value,
                dropid: document.getElementById("dropid").value,
                bdate: new Date().toISOString().split("T")[0] // Current date
            };

            fetch('/MegaCityCab/api/booking/addBooking', {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData)
            })
            .then(response => response.text())
            .then(data => {
                document.getElementById("message").innerHTML = `<div class="alert alert-success">${data}</div>`;
            })
            .catch(error => {
                document.getElementById("message").innerHTML = `<div class="alert alert-danger">Error: ${error}</div>`;
            });
        });
    });
</script>
</body>
</html>
