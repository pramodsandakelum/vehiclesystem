<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Destination</title>     
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <script src="destination.js"></script>
        <script>
            window.onload = function () {               
                    loadDestination();         
            };
        </script>
    </head>
    <body>

        <div class="container mt-4">
            <h2>Manage Destination</h2>

            <form id="destinationForm">
                
                <label class="form-label">Destination Name:</label>
                <input type="text" id="city_name" class="form-control" required>

                <label class="form-label">Latitude</label>
                <input type="number" id="city_latitude" class="form-control" required>

                <label class="form-label">Longitude</label>
                <input type="number" id="city_longitude" class="form-control" required>

                <button onclick="saveDestination()"class="btn btn-primary mt-3">Save Destination</button>
            </form><br>

            <table class="table table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>CID</th>
                        <th>Name</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="destinationTableBody">
                </tbody>
            </table>
        </div>

        <div class="modal fade" id="editDestinationModal" tabindex="-1" aria-labelledby="editDestinationModal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editDestinationModalLabel">Edit Destination</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="editDestinationForm">
                            <input type="hidden" id="e_cid">

                            <label class="form-label">Destination Name:</label>
                            <input type="text" id="e_city_name" class="form-control" required>

                            <label class="form-label">Latitude</label>
                            <input type="number" id="e_city_latitude" class="form-control" required>

                            <label class="form-label">Longitude</label>
                            <input type="number" id="e_city_longitude" class="form-control" required>


                            <button onclick="updateDestination()" class="btn btn-success mt-3">Update Destination</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


    </body>
</html>
