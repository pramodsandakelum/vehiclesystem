
async function loadVehicles() {
    let response = await fetch("http://localhost:8080/Mega_city_cabs/api/vehicle/getAllVehicle");
    let vehicles = await response.json();

    let tableBody = document.getElementById("vehicleTableBody");
    tableBody.innerHTML = ""; // Clear previous rows
    
    vehicles.forEach(v => {
        let rowClass = v.booked ? "table-danger" : "";
        let row = `<tr class="${rowClass}">
            <td>${v.vid}</td>
            <td>${v.type}</td>
            <td>${v.number}</td>
            <td>${v.booked ? "Yes" : "No"}</td>
            <td>
            <button class="btn btn-warning btn-sm" 
                onclick="openEditModal(${v.vid}, '${v.type}', '${v.number}')"
                ${v.booked ? "disabled" : ""}>
                Edit
            </button>
            <button class="btn btn-danger btn-sm" 
                onclick="deleteVehicle(${v.vid})"
                ${v.booked ? "disabled" : ""}>
                Delete
            </button>
        </td>
        </tr>`;
        tableBody.innerHTML += row;
    });
}

async function saveVehicle(){
    let type = document.getElementById("type").value;
    let number = document.getElementById("number").value;

    let vehicleData = { type, number, booked: false };

    let response = await fetch("http://localhost:8080/Mega_city_cabs/api/vehicle/addVehicle", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vehicleData)
    });

    let result = await response.json();
    alert(result.message);
    document.getElementById("vehicleForm").reset();
    loadVehicles();

}

function openEditModal(id, type, number) {
    document.getElementById("editVid").value = id;
    document.getElementById("editType").value = type;
    document.getElementById("editNumber").value = number;

    var editModal = new bootstrap.Modal(document.getElementById('editVehicleModal'));
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

async function deleteVehicle(id) {
    if (confirm("Are you sure you want to delete this vehicle?")) {
        let response = await fetch(`http://localhost:8080/Mega_city_cabs/api/vehicle/deleteVehicle/${id}`);
        let result = await response.json();
        alert(result.message);
        loadVehicles();
    }
}

document.addEventListener("DOMContentLoaded", loadVehicles);
