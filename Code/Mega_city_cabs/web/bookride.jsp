<%@ page session="true" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
        <script>
            window.onload = function () {
                var uid = '<%= session.getAttribute("uid")%>';
                document.getElementById("cid").textContent = uid;
                getUserName(uid);
                loadDestinations();
                loadDriversAndVehicles();
                let today = new Date().toISOString().split("T")[0];
                document.getElementById("bdate").setAttribute("min", today);
            }
        </script>

        <div class="container mt-5">
            <h2 class="text-center">Book a Ride</h2>
            <form id="bookingForm">
                <div class="row">
                    <div id="message" class="mt-3"></div>
                    <div class="mb-3">
                        <label for="cid" class="form-label">Customer ID:</label>
                        <b><label id="cid" class="form-label"></label></b><br>
                        <label for="cname" class="form-label">Customer Name:</label>
                        <b><label id="name" class="form-label"></label></b><br>
                    </div>
                    
                    <div class="col-md-6">
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
                            <input type="text" class="form-control" id="distance" name="distance" disabled>
                        </div>

                        <div class="mb-3">
                            <label for="price" class="form-label">Price (LKR)</label>
                            <input type="number" class="form-control" id="price" name="price" disabled>
                        </div>

                        <div class="mb-3">
                            <button type="button" class="btn btn-success w-100" onclick="calculateFare()">Calculate Fare</button>
                        </div>
                    </div>

                    <!-- Right Column -->
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="bdate" class="form-label">Booking Date:</label>
                            <input type="date" class="form-control" id="bdate" name="bdate" required>
                        </div>

                        <div class="mb-3">
                            <label for="vid" class="form-label">Vehicle Type:</label>
                            <select onchange="selectType()" class="form-control" id="type" name="vid" required>
                                <option value="">Select Vehicle Type</option>
                                <option value="Car">Car</option>
                                <option value="Van">Van</option>
                            </select>
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

                        <div class="mb-3">
                            <button onclick="createBooking()" class="btn btn-primary w-100">Confirm Booking</button>
                        </div>

                        
                    </div>
                </div>
            </form>
        </div>

        

    </body>
</html>
