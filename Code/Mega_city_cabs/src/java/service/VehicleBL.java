package service;

import model.Vehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleBL {

    public DBHandler dbManager = DBHandler.getInstance();
    public Connection dbConnection = dbManager.getConnection();

    // 🚗 Get Vehicle by ID
    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Vehicle";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVid(result.getInt("vid"));
                vehicle.setType(result.getString("type"));
                vehicle.setNumber(result.getString("number"));
                vehicle.setBooked(result.getBoolean("booked"));
                vehicleList.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }

    // 🚗 Insert New Vehicle
    public boolean insertVehicle(Vehicle vehicle) {
        try {
            String query = "INSERT INTO Vehicle (type, number, booked) VALUES (?, ?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, vehicle.getType());
            statement.setString(2, vehicle.getNumber());
            statement.setBoolean(3, vehicle.getBooked());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateVehicle(Vehicle vehicle) {
        try {
            String query = "UPDATE Vehicle SET type=?, number=?, booked=? WHERE vid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, vehicle.getType());
            statement.setString(2, vehicle.getNumber());
            statement.setBoolean(3, vehicle.getBooked());
            statement.setInt(4, vehicle.getVid());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
