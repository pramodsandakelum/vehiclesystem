/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.Booking;
import model.Destination;
import model.Driver;
import model.billCalculateDTO;
import model.bookingDetailDTO;

/**
 *
 * @author pramo
 */
public class BookingBL {

    public DBHandler dbManager = DBHandler.getInstance();
    public Connection dbConnection = dbManager.getConnection();
    
    private final UserBL userbl = new UserBL();

    public billCalculateDTO calculateBooking(int pickup, int drop) {
        final double earthRadius = 6378;
        double fixedCharge = 500;
        double chargePerKM = 120;
        double taxrate = 5;// this value is 5%
        double latDistance = 0;
        double lonDistance = 0;
        double latitude1 = 0;
        double longitude1 = 0;
        double latitude2 = 0;
        double longitude2 = 0;
        int locationCount = 0;
        billCalculateDTO bookinginfo = new billCalculateDTO();
        try {
            String query = "SELECT city_latitude, city_longitude FROM Destination WHERE cid=? or cid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, pickup);
            statement.setInt(2, drop);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                if (locationCount == 0) {
                    latitude1 = result.getDouble("city_latitude");
                    longitude1 = result.getDouble("city_longitude");
                } else {
                    latitude2 = result.getDouble("city_latitude");
                    longitude2 = result.getDouble("city_longitude");
                }
                locationCount++;

            }
            if (locationCount < 2) {
                return null;
            }
            //Haversine Formula to calculate distance using latitudes and longitudes
            latDistance = Math.toRadians(latitude2 - latitude1);
            lonDistance = Math.toRadians(longitude2 - longitude1);

            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            // end formula
            Double totalDistance = earthRadius * c;
            Double taxifare = fixedCharge + (chargePerKM * totalDistance);
            Double serviceCharge = (taxifare / 100) * taxrate;

            bookinginfo.setFixedCharge(fixedCharge);
            bookinginfo.setKm(Math.round(totalDistance * 100.0) / 100.0);
            bookinginfo.setServiceCharge(Math.round(serviceCharge * 100.0) / 100.0);
            bookinginfo.setTaxiFare(Math.round(taxifare * 100.0) / 100.0);
            bookinginfo.setChargePerKM(chargePerKM);

            return bookinginfo;

        } catch (SQLException e) {
            return null;
        }
    }

    public List<bookingDetailDTO> getAllBookings() {
        try {
            List<bookingDetailDTO> bookingList = new ArrayList<>();

            String Query = "SELECT * FROM Booking";
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery(Query);
            while (result.next()) {
                bookingDetailDTO booking = new bookingDetailDTO();
                UserBL user = new UserBL();
                VehicleBL vehicle = new VehicleBL();
                DestinationBL destination = new DestinationBL();

                booking.setBid(result.getInt("bid"));
                booking.setBcode(result.getString("bcode"));
                booking.setCustomerName(user.returnUserName(result.getInt("cid")));
                booking.setDriverName(user.returnUserName(result.getInt("did")));
                booking.setVehicleNumber(vehicle.returnVehicleNo(result.getInt("vid")));
                booking.setVehicleType(vehicle.returnVehicleType(result.getInt("vid")));
                booking.setPickupLocation(destination.returnPickup(result.getInt("pickupid")));
                booking.setDropLocation(destination.returnDrop(result.getInt("dropid")));
                booking.setDistance(result.getDouble("distance"));
                booking.setFare(result.getDouble("fare"));
                booking.setServiceCharge(result.getDouble("serviceCharge"));
                booking.setFixedCharge(result.getDouble("fixedCharge"));
                booking.setChargePerKM(result.getDouble("chargePerKM"));
                booking.setApproved(result.getBoolean("Approved"));
                bookingList.add(booking);
            }

            return bookingList;

        } catch (SQLException e) {
            return null;
        }

    }

    public String addBooking(Booking booking) {
        String message = "Error Adding Booking";
        billCalculateDTO charges = new billCalculateDTO();
        UserBL userbl = new UserBL();
        try {
            String query = "INSERT INTO Booking (bcode, cid, did, bdate, vid, pickupid, dropid, distance, fare, serviceCharge, fixedCharge, chargePerKM, Approved) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            String bookingCode = "MCB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            PreparedStatement statement = dbConnection.prepareStatement(query);
            //from backend
            statement.setString(1, bookingCode);
            //from frontend
            statement.setInt(2, booking.getCid());//customer id from front
            statement.setInt(3, booking.getDid());//driver id from front
            statement.setDate(4, booking.getBdate());//get date from front
            statement.setInt(5, booking.getVid());//vehicle id from front
            statement.setInt(6, booking.getPickupid());//pickup location from front
            statement.setInt(7, booking.getDropid());//drop location from front
            //hack prevention extra security
            //calculate distance and fare in backend using pickup and drop location
            charges = calculateBooking(booking.getPickupid(), booking.getDropid());
            statement.setDouble(8, charges.getKm());//trip distance
            statement.setDouble(9, charges.getTaxiFare());// total fare
            statement.setDouble(10, charges.getServiceCharge());
            statement.setDouble(11, charges.getFixedCharge());
            statement.setDouble(12, charges.getChargePerKM());
            statement.setBoolean(13, false);
            
            int result = statement.executeUpdate();
            if(result>0){
                //update driver profile setting driver unavailable
                Driver driver = new Driver();
                driver.setId(booking.getDid());
                driver.setName(userbl.returnUserName(booking.getDid()));
                driver.setIs_available(false);
                userbl.updateDriverProfile(driver);
                message = "Booking Created Successfully";
            }
            return message;
        } catch (SQLException e) {
            return "Internal server Error" + e.getMessage();
        }
    }

}
