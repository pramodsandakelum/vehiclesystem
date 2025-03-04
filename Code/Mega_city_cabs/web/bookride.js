
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
    
    
    function calculateFare(){
        
        alert("fare");
    }


