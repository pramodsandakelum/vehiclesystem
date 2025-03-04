
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
                cid.textContent = data.fname +" "+data.lname;
        
            })
            .catch(error => console.error("Error loading destinations:", error));
    }
    
    
function calculateFare(){
        
        
        let pickupID = document.getElementById("pickupid").value;
        let dropID = document.getElementById("dropid").value;
        
        
        
        if(pickupID == dropID){
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
    
    

        // Example vehicles
        let vehicleSelect = document.getElementById("vid");
        ["101", "102", "103"].forEach(id => {
            vehicleSelect.appendChild(new Option("Vehicle " + id, id));
        });
    }
