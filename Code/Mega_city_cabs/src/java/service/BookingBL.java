/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.spi.DirStateFactory;
import model.Destination;
import model.bookinginfoDTO;

/**
 *
 * @author pramo
 */
public class BookingBL {

    public DBHandler dbManager = DBHandler.getInstance();
    public Connection dbConnection = dbManager.getConnection();

    public List<Destination> getAllDestination() {

        List<Destination> locations = new ArrayList<>();
        try {
            String query = "SELECT * FROM Destination";
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Destination location = new Destination();
                location.setCid(result.getInt("cid"));
                location.setCity_name(result.getString("city_name"));
                location.setCity_latitude(result.getDouble("city_latitude"));
                location.setCity_longitude(result.getDouble("city_longitude"));
                locations.add(location);
            }
            return locations;
        } catch (Exception e) {
            return null;
        }

    }

    public bookinginfoDTO calculateBooking(int pickup, int drop) {
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
        bookinginfoDTO bookinginfo = new bookinginfoDTO();
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
            
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            
            // end formula
            
            Double totalDistance = earthRadius*c;
            Double taxifare = fixedCharge + (chargePerKM * totalDistance);
            Double serviceCharge = (taxifare/100) * taxrate;
            
            bookinginfo.setFixedCharge(fixedCharge);
            bookinginfo.setKm(Math.round(totalDistance*100.0)/100.0);
            bookinginfo.setServiceCharge(Math.round(serviceCharge*100.0)/100.0);
            bookinginfo.setTaxiFare(Math.round(taxifare*100.0)/100.0);
            
            return bookinginfo;

        } catch (SQLException e) {
            return null;
        }
    }

}
