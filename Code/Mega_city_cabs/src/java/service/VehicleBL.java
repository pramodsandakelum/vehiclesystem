/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Vehicle;

/**
 *
 * @author pramo
 */
public class VehicleBL {
    
    public DBHandler dbManager = DBHandler.getInstance();
    public Connection dbConnection = dbManager.getConnection();
    
    public String returnVehicleNo(int id){
        String number = "";
        try {
            String query = "SELECT number from Vehicle WHERE vid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {                
                number = result.getString("number");
            }
            return number;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public String returnVehicleType(int id){
        String Type = "";
        try {
            String query = "SELECT type from Vehicle WHERE vid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {                
                Type = result.getString("type");
            }
            return Type;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Vehicle";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVid(result.getInt("vid"));
                vehicle.setType(result.getString("type"));
                vehicle.setNumber(result.getString("number"));
                vehicle.setBooked(result.getBoolean("booked"));
                vehicleList.add(vehicle);
            }
        } catch (SQLException e) {
            return null;
        }
        return vehicleList;
    }

    
    public boolean insertVehicle(Vehicle vehicle) {
        try {
            String query = "INSERT INTO Vehicle (type, number, booked) VALUES (?, ?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, vehicle.getType());
            statement.setString(2, vehicle.getNumber());
            statement.setBoolean(3, false);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            //e.printStackTrace();
            return false;
        }
        
    }


    public boolean updateVehicle(Vehicle vehicle) {
        try {
            String query = "UPDATE Vehicle SET type=?, number=?, booked=? WHERE vid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, vehicle.getType());
            statement.setString(2, vehicle.getNumber());
            statement.setBoolean(3, false);
            statement.setInt(4, vehicle.getVid());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteVehicle(int vid) {
        try {
            String query = "DELETE FROM Vehicle WHERE vid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, vid);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
