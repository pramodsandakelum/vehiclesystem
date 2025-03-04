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
                <button class="btn btn-warning btn-sm" onclick="openEditModal(${v.vid}, '${v.type}', '${v.number}', ${v.booked})">Edit</button>
                <button class="btn btn-danger btn-sm" onclick="deleteVehicle(${v.vid})">Delete</button>
            </td>
        </tr>`;
        tableBody.innerHTML += row;
    });
}

// 🚗 Handle Form Submission (Add New Vehicle)
document.getElementById("vehicleForm").onsubmit = async function(event) {
    event.preventDefault();

    let type = document.getElementById("type").value;
    let number = document.getElementById("number").value;

    let vehicleData = { type, number, booked: false };

    let response = await fetch("api/vehicle/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vehicleData)
    });

    let result = await response.text();
    alert(result);
    document.getElementById("vehicleForm").reset();
    loadVehicles();
};

// ✏️ Open Edit Modal with Data
function openEditModal(id, type, number, booked) {
    document.getElementById("editVid").value = id;
    document.getElementById("editType").value = type;
    document.getElementById("editNumber").value = number;
    document.getElementById("editBooked").value = booked;

    var editModal = new bootstrap.Modal(document.getElementById('editVehicleModal'));
    editModal.show();
}

// ✅ Handle Update Vehicle
document.getElementById("editVehicleForm").onsubmit = async function(event) {
    event.preventDefault();

    let vid = document.getElementById("editVid").value;
    let type = document.getElementById("editType").value;
    let number = document.getElementById("editNumber").value;
    let booked = document.getElementById("editBooked").value;

    let vehicleData = { type, number, booked };

    let response = await fetch(`api/vehicle/update/${vid}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vehicleData)
    });

    let result = await response.text();
    alert(result);

    var editModal = bootstrap.Modal.getInstance(document.getElementById('editVehicleModal'));
    editModal.hide();

    loadVehicles();
};

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
document.addEventListener("DOMContentLoaded", loadVehicles);
