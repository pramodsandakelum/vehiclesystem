
function loadDestinations() {
    fetch('http://localhost:8080/Mega_city_cabs/api/booking/getAllDestination')
            .then(response => response.json())
            .then(data => {
                let pickupSelect = document.getElementById("pickupid");
                let dropSelect = document.getElementById("dropid");

                data.forEach(location => {
                    let option = new Option(location.city_name, location.cid);
                    pickupSelect.appendChild(option.cloneNode(true));
                    dropSelect.appendChild(option);
                });
            })
            .catch(error => console.error("Error loading destinations:", error));
}


function getUserName(id) {
    fetch(`http://localhost:8080/Mega_city_cabs/api/user/getUserByID/${id}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let cid = document.getElementById("name");
                cid.textContent = data.fname + " " + data.lname;

            })
            .catch(error => console.error("Error loading destinations:", error));
}


function calculateFare() {


    let pickupID = document.getElementById("pickupid").value;
    let dropID = document.getElementById("dropid").value;



    if (pickupID == dropID) {
        alert("Same Destination");
    }
    fetch(`http://localhost:8080/Mega_city_cabs/api/booking/calculateFare/${pickupID}/${dropID}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let totalPrice = document.getElementById("price");
                totalPrice.value = data.taxiFare;
                let totalDistance = document.getElementById("distance");
                totalDistance.value = data.km + ' Km';
            })
            .catch(error => console.error("Error fetching bookings:", error));
}

function loadDriversAndVehicles() {
    // load drivers
    fetch('http://localhost:8080/Mega_city_cabs/api/user/getAllDrivers')
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let driverSelect = document.getElementById("did");

                data.forEach(driver => {
                    let option = new Option(driver.name, driver.id);
                    driverSelect.appendChild(option);
                });
            })
            .catch(error => console.error("Error loading destinations:", error));



}

function selectType() {
    let type = document.getElementById("type").value;
    getbyType(type);
}

function getbyType(type) {
    fetch(`http://localhost:8080/Mega_city_cabs/api/vehicle/getVehicleByType/${type}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let vehicleSelect = document.getElementById("vid");
                vehicleSelect.innerHTML = '<option value="">Select Vehicle</option>';
                data.forEach(vehicle => {
                    let option = new Option(vehicle.number, vehicle.vid);
                    vehicleSelect.appendChild(option);
                });
            })
            .catch(error => console.error("Error loading destinations:", error));

}

function createBooking() {
    let pickupid = document.getElementById("pickupid").value;
    let dropid = document.getElementById("dropid").value;
    let cid = document.getElementById("cid").textContent;
    let did = document.getElementById("did").value;
    let vid = document.getElementById("vid").value;
    let bdate = document.getElementById("bdate").value;

    if (!pickupid || !dropid || !cid || !did || !vid || !bdate) {
        document.getElementById("message").innerHTML =
                `<div class="alert alert-danger">All fields are required!</div>`;
        return;
    }

    let booking = {
        cid: parseInt(cid, 10),
        did: parseInt(did, 10),
        vid: parseInt(vid, 10),
        pickupid: parseInt(pickupid, 10),
        dropid: parseInt(dropid, 10),
        bdate: bdate
    };


    fetch('http://localhost:8080/Mega_city_cabs/api/booking/addBooking', {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(booking)
    })
            .then(response => {
                console.log("Response received:", response);
                return response.text(); // Log raw response before parsing JSON
            })
            .then(text => {
                console.log("Raw response text:", text);
                return text ? JSON.parse(text) : {}; // Prevent JSON parse errors if empty
            })
            .then(data => {
                console.log("Success book");
                console.log(data);
                document.getElementById("message").innerHTML =
                        `<div class="alert alert-success">${data.message || "Booking Added Successfully"}</div>`;
            })
            .catch(error => {
                console.error("Fetch error:", error);
                document.getElementById("message").innerHTML =
                        `<div class="alert alert-danger">Error: ${error}</div>`;
            });
            alert("Booking Successful");

}

