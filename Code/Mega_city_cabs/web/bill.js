function fetchBills(userRole, uid) {
    let url = "http://localhost:8080/Mega_city_cabs/api/booking/getallBills";

    fetch(url)
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById("billTableBody");
                tableBody.innerHTML = "";
                console.log(data);
                data.forEach(bill => {
                    let isPaid = bill.paid;
                    let actionButtons = "";

                    if (userRole === 1 || userRole === 3) {  // Admin and Customer can pay
                        actionButtons = `<button class="btn btn-success btn-sm" onclick="paybill(${bill.bid},${userRole},${uid})" ${isPaid ? "disabled" : ""}>Pay Bill</button>
                                         <button class="btn btn-info btn-sm" onclick="viewBillDetails(${bill.bid})">View Bill</button>`;
                    } else {  // Driver can only view the bill (can't pay)
                        actionButtons = `<button class="btn btn-danger btn-sm" disabled>Pay Bill</button>
                                         <button class="btn btn-info btn-sm" onclick="viewBillDetails(${bill.bid})">View Bill</button>`;
                    }
                    const row = `
                    <tr>
                        <td style='display:none;'>${bill.bid}</td>
                        <td>${new Date(bill.bdate).toLocaleDateString()}</td>
                        <td>${bill.driver}</td>                       
                        <td>${bill.vehicle}</td>
                        <td>${bill.pickupl}</td>
                        <td>${bill.dropl}</td>
                        <td>${bill.distance}</td>
                        <td>${bill.fare}</td>                      
                        <td>${isPaid ? "<span class='badge bg-success'>Yes</span>" : "<span class='badge bg-danger'>No</span>"}</td>
                        <td>${actionButtons}</td>
                    </tr>
                `;

                    tableBody.innerHTML += row;
                });
            })
            .catch(error => console.error("Error fetching bills:", error));
}


function viewBillDetails(bid) {
    alert(bid);
    fetch(`http://localhost:8080/Mega_city_cabs/api/booking/getBillById/${bid}`)
        .then(response => response.json())
        .then(bill => {
                console.log(bill);
                console.log(bill.bid);
            // Populate modal fields
            document.getElementById("modalBillNo").textContent = bill.bid;
            document.getElementById("modalDate").value = new Date(bill.bdate).toLocaleDateString();
            document.getElementById("modalDriver").value = bill.driver;
            document.getElementById("modalVehicle").value = bill.vehicle;
            document.getElementById("modalPickup").value = bill.pickupl;
            document.getElementById("modalDrop").value = bill.dropl;
            document.getElementById("modalDistance").value = bill.distance;
            document.getElementById("modalFare").value = bill.fare;
            document.getElementById("modalPaid").value = bill.paid ? "<span class='badge bg-success'>Yes</span>" : "<span class='badge bg-danger'>No</span>";
            
            // Show the modal
            let myModal = new bootstrap.Modal(document.getElementById('billModal'), {});
            myModal.show();
        })
        .catch(error => console.error("Error fetching bill details:", error));
}
