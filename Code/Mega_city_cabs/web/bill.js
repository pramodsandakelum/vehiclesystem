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
                        actionButtons = `<button class="btn btn-info btn-sm" onclick="viewBillDetails(${bill.bid},'${new Date(bill.bdate).toLocaleDateString()}', '${bill.driver}', '${bill.vehicle}', '${bill.pickupl}', '${bill.dropl}', ${bill.distance}, ${bill.fare}, ${bill.paid})">View Bill</button>`;
                    } else {  // Driver can only view the bill (can't pay)
                        actionButtons = `<button class="btn btn-info btn-sm" onclick="viewBillDetails(${bill.bid},'${new Date(bill.bdate).toLocaleDateString()}', '${bill.driver}', '${bill.vehicle}', '${bill.pickupl}', '${bill.dropl}', ${bill.distance}, ${bill.fare}, ${bill.paid})">View Bill</button>`;
                    }

                    const row = `
                    <tr>
                        <td>${bill.bid}</td>
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



function viewBillDetails(bid, bdate, driver, vehicle, pickupl, dropl, distance, fare, paid) {

    // Populate modal fields
    document.getElementById("modalBillNo").textContent = bid;
    document.getElementById("modalDate").textContent = bdate;
    document.getElementById("modalDriver").textContent = driver;
    document.getElementById("modalVehicle").textContent = vehicle;
    document.getElementById("modalPickup").textContent = pickupl;
    document.getElementById("modalDrop").textContent = dropl;
    document.getElementById("modalDistance").textContent = distance;
    document.getElementById("modalFare").textContent = fare;
    document.getElementById("modalPaid").innerHTML = paid ? "<span class='badge bg-success'>Yes</span>" : "<span class='badge bg-danger'>No</span>";

    // Show the modal
    let myModal = new bootstrap.Modal(document.getElementById('billModal'), {});
    myModal.show();
}

function applyPromoCode() {
    let fareElement = document.getElementById("modalFare");
    let fare = parseFloat(fareElement.textContent);
    let promocode = document.getElementById("promoCode").value.trim();
    let discount = 0;
    let priceAfter = fare;

    if (promocode === "MCB10") {
        discount = fare * 0.10; // 10% discount
        priceAfter = fare - discount;
    } else if (promocode === "MCB25") {
        discount = fare * 0.25; // 25% discount
        priceAfter = fare - discount;
    } else {
        alert("Invalid promo code!");
        return;
    }

    priceAfter = Math.max(0, priceAfter);

    fareElement.textContent = priceAfter.toFixed(2);

    alert(`Promo code applied! New fare: ${priceAfter.toFixed(2)}`);
}


function printBill() {
    let modalContent = document.querySelector('.modal-body').cloneNode(true);

    modalContent.querySelectorAll("#promoRow, button, input").forEach(el => el.remove());
    let printWindow = window.open('', '', 'height=800,width=1200');
    printWindow.document.write(`
        <html>
        <head>
            <title>Print Bill</title>
            <style>
                body { 
                    font-family: Arial, sans-serif; 
                    background: #f4f4f4; 
                    padding: 40px; 
                    text-align: center;
                }
                .bill-container { 
                    max-width: 600px; 
                    margin: auto; 
                    background: #fff; 
                    padding: 20px; 
                    border-radius: 10px; 
                    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
                    border: 5px solid #007bff; 
                }
                h2 { 
                    text-align: center; 
                    color: #007bff; 
                }
                .logo {
                    text-align: center;
                    margin-bottom: 10px;
                }
                .logo img {
                    width: 100px;
                }
                table { 
                    width: 100%; 
                    border-collapse: collapse; 
                    margin-top: 20px; 
                }
                th, td { 
                    padding: 10px; 
                    border-bottom: 1px solid #ddd; 
                    font-size: 16px;
                }
                .total { 
                    font-weight: bold; 
                    text-align: right; 
                    font-size: 18px; 
                    color: #28a745;
                }
                .thank-you {
                    text-align: center;
                    font-size: 16px;
                    margin-top: 15px;
                    color: #555;
                }
            </style>
        </head>
        <body>
            <div class="bill-container">
                <div class="logo">
                    <img src="https://cdn-icons-png.flaticon.com/512/846/846449.png" alt="Company Logo">
                </div>
                <h2>Mega City Cabs - Bill Receipt</h2>
                ${modalContent.innerHTML} <!-- Insert cleaned modal content -->
                <p class="total">Thank you for choosing Mega City Cabs!</p>
                <p class="thank-you">Safe Travels! 🚖</p>
            </div>
        </body>
        </html>
    `);

    printWindow.document.close();
    printWindow.print();
}

function paybill() {
    alert('test');

    let finalFare = document.getElementById("modalFare").textContent;
    let billid = document.getElementById("modalBillNo").textContent;
    
    const url = "http://localhost:8080/Mega_city_cabs/api/booking/payBill";
    
    let bill = {
      bid:billid,
      fare:finalFare,
      paid:true
    };
    
    fetch(url, {
        method: 'POST', 
        headers: {
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify(bill)
    })
    .then(response => response.json())
    .then(data => {
        
        console.log(data.message);
        alert(data.message);
    })
    .catch(error => {
        console.error("Error while paying the bill:", error);
        alert("Error occurred while processing payment.");
    });
}


