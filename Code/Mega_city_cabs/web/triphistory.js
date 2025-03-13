function fetchBookings(userRole, uid) {
    let url = "";
    alert("urole "+userRole);
   
    if (userRole === 1) {
        url = "http://localhost:8080/Mega_city_cabs/api/booking/getallBooking";  // Admin
    } else if (userRole === 2) {
        url = `http://localhost:8080/Mega_city_cabs/api/booking/getallBookingByDID/${uid}`;  // Driver
    } else {
        url = `http://localhost:8080/Mega_city_cabs/api/booking/getallBookingByCID/${uid}`;  // Customer
    }

    
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("bookingTableBody");
            tableBody.innerHTML = ""; 
            console.log(data);

            
            data.forEach(booking => {
                let isApproved = booking.approved;
                let actionButtons = "";

               
                if (userRole === 1 || userRole === 2) {  // Admin and Driver can End Trip
                    actionButtons = `<button class="btn btn-success btn-sm" onclick="handleTripAction(${booking.bid}, 'End', ${userRole},${uid})" ${!isApproved ? "disabled" : ""}>End Trip</button>`;
                } else if (userRole === 3) {  // Customer can Cancel Trip
                    actionButtons = `<button class="btn btn-danger btn-sm" onclick="handleTripAction(${booking.bid}, 'Cancel', ${userRole},${uid})" ${!isApproved ? "disabled" : ""}>Cancel Trip</button>`;
                }

                
                const row = `
                    <tr>
                        <td style='display:none;'>${booking.bid}</td>
                        <td>${booking.bcode}</td>
                        <td>${new Date(booking.bdate).toLocaleDateString()}</td>
                        <td>${booking.customerName}</td>
                        <td>${booking.driverName}</td>
                        <td>${booking.vehicleType} - ${booking.vehicleNumber}</td>
                        <td>${booking.pickupLocation}</td>
                        <td>${booking.dropLocation}</td>
                        <td>${booking.distance}</td>
                        <td>${booking.fare}</td>
                        <td>${isApproved ? "<span class='badge bg-success'>Yes</span>" : "<span class='badge bg-danger'>No</span>"}</td>
                        <td>${actionButtons}</td>
                    </tr>
                `;

                tableBody.innerHTML += row; 
            });
        })
        .catch(error => console.error("Error fetching bookings:", error));
}

function handleTripAction(bid, action, role,uid) {
    fetch("http://localhost:8080/Mega_city_cabs/api/booking/updateTripStatus", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            bid: bid,
            action: action
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (data.message) {
                alert(data.message);
                fetchBookings(role,uid); 
            } else {
                alert(`Failed to ${action} trip.`); 
            }
        })
        .catch(error => console.error("Error:", error));
}
