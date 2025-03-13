<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Trip Details</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="bill.js"></script>
        <script src="commonFunctions.js"></script>
        <script>
            let inactivityTimer;
            function resetInactivityTimer() {
                console.log("reset");
                clearTimeout(inactivityTimer);
                inactivityTimer = setTimeout(function () {
                    if (window.parent && window.parent.autoLogout) {
                        window.parent.autoLogout();
                    } else {
                        console.error("autoLogout function not found in parent window.");
                    }
                }, 900000); // 15 mins logout
            }

            window.onload = resetInactivityTimer;
            document.onmousemove = resetInactivityTimer;
            document.onkeypress = resetInactivityTimer;
            document.onclick = resetInactivityTimer;
        </script>
        <script>
            window.onload = function () {
                let role = <%= session.getAttribute("role")%>;
                let uid = <%= session.getAttribute("uid")%>;
                fetchBills(role, uid);

                // Disable Pay Bill button for role 2 (Driver)
                if (role === 2) {
                    const payButton = document.getElementById("payBillButton");
                    if (payButton) {
                        payButton.disabled = true;
                    }
                }
            };
        </script>
    </head>
    <body class="container mt-4">

        <h2 class="text-center mb-4">Billing Details</h2>

        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>Bill No</th>
                        <th>Date</th>
                        <th>Driver</th>
                        <th>Vehicle</th>
                        <th>Pickup</th>
                        <th>Drop</th>
                        <th>Distance (km)</th>
                        <th>Fare</th>
                        <th>Paid</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="billTableBody">

                </tbody>
            </table>

            <div class="modal fade" id="billModal" tabindex="-1" aria-labelledby="billModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="billModalLabel">Bill Details</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <table class="table">
                                <tr>
                                    <th>Bill No</th>
                                    <td><label id="modalBillNo"></label></td>
                                </tr>
                                <tr>
                                    <th>Date</th>
                                    <td><label id="modalDate"></label></td>
                                </tr>
                                <tr>
                                    <th>Driver</th>
                                    <td><label id="modalDriver"></label></td>
                                </tr>
                                <tr>
                                    <th>Vehicle</th>
                                    <td><label id="modalVehicle"></label></td>
                                </tr>
                                <tr>
                                    <th>Pickup</th>
                                    <td><label id="modalPickup"></label></td>
                                </tr>
                                <tr>
                                    <th>Drop</th>
                                    <td><label id="modalDrop"></label></td>
                                </tr>
                                <tr>
                                    <th>Distance (km)</th>
                                    <td><label id="modalDistance"></label></td>
                                </tr>
                                <tr>
                                    <th>Fare</th>
                                    <td><label id="modalFare"></label></td>
                                </tr>
                                <tr>
                                    <th>Paid</th>
                                    <td><label id="modalPaid"></label></td>
                                </tr>
                                <tr id="promoRow">
                                    <th>Promo Code</th>
                                    <td>
                                        <input type="text" id="promoCode" class="form-control" placeholder="Enter promo code">
                                        <button class="btn btn-primary btn-sm mt-2" onclick="applyPromoCode()">Apply</button>
                                    </td>
                                </tr>
                            </table>
                            <button class="btn btn-primary" onclick="printBill()">Print Bill</button>
                            <button id="payBillButton" class="btn btn-success btn-primary" onclick="paybill(${isPaid ? 'disabled' : ''})">Pay Bill</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
