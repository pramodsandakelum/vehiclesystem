
async function loadDestination() {
   
    let response = await fetch("http://localhost:8080/Mega_city_cabs/api/destination/getAllDestination");
    let destination = await response.json();

    let tableBody = document.getElementById("destinationTableBody");
    tableBody.innerHTML = ""; 
    destination.forEach(d => {
        let row = `<tr>
            <td>${d.cid}</td>
            <td>${d.city_name}</td>
            <td>${d.city_latitude}</td>
            <td>${d.city_longitude}</td>
            <td>
            <button class="btn btn-warning btn-sm" 
                onclick="openEditModal(${d.cid}, '${d.city_name}', '${d.city_latitude}','${d.city_longitude}')">
                Edit
            </button>
            <button class="btn btn-danger btn-sm" 
                onclick="deleteDestination(${d.cid})">
                Delete
            </button>
        </td>
        </tr>`;
        tableBody.innerHTML += row;
    });
}

async function saveDestination(){
    let city_name = document.getElementById("city_name").value;
    let city_latitude = document.getElementById("city_latitude").value;
    let city_longitude = document.getElementById("city_longitude").value;

    let destinationData = { 
        city_name, 
        city_latitude, 
        city_longitude 
    };

    let response = await fetch("http://localhost:8080/Mega_city_cabs/api/destination/addDestination", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(destinationData)
    });

    let result = await response.json();
    alert(result.message);
    document.getElementById("destinationForm").reset();
    loadDestination();

}

function openEditModal(cid, city_name, city_latitude,city_longitude) {
    document.getElementById("e_cid").value = cid;
    document.getElementById("e_city_name").value = city_name;
    document.getElementById("e_city_latitude").value = city_latitude;
    document.getElementById("e_city_longitude").value = city_longitude;

    var editModal = new bootstrap.Modal(document.getElementById('editDestinationModal'));
    editModal.show();
}

async function updateVehicle(){
    let vid = document.getElementById("editVid").value;
    let type = document.getElementById("editType").value;
    let number = document.getElementById("editNumber").value;
    let booked = false;

    let vehicleData = { vid, type, number,booked};

    let response = await fetch(`http://localhost:8080/Mega_city_cabs/api/vehicle/updateVehicle`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vehicleData)
    });
    
    let result = await response.json();

    
    alert(result.message);

    var editModal = bootstrap.Modal.getInstance(document.getElementById('editVehicleModal'));
    editModal.hide();

    loadVehicles();
}

async function deleteDestination(id) {
    if (confirm("Are you sure you want to delete this destination?")) {
        let response = await fetch(`http://localhost:8080/Mega_city_cabs/api/destination/deleteDestination/${id}`);
        let result = await response.json();
        alert(result.message);
        loadDestination();
    }
}


