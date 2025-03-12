package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Destination;

public class DestinationBL {
    
    private final DBHandler dbManager = DBHandler.getInstance();
    private final Connection dbConnection = dbManager.getConnection();
    
    public String returnPickup(int id) {
        String pickup = "";
        try {
            String query = "SELECT city_name FROM Destination WHERE cid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                pickup = result.getString("city_name");
            }
        } catch (SQLException e) {
        }
        return pickup;
    }
    
    public String returnDrop(int id) {
        String drop = "";
        try {
            String query = "SELECT city_name FROM Destination WHERE cid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                drop = result.getString("city_name");
            }
        } catch (SQLException e) {
        }
        return drop;
    }
    
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
        } catch (SQLException e) {
        }
        return locations;
    }
    
    public boolean addDestination(Destination destination) {
        String query = "INSERT INTO Destination (city_name, city_latitude, city_longitude) VALUES (?, ?, ?)";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, destination.getCity_name());
            statement.setDouble(2, destination.getCity_latitude());
            statement.setDouble(3, destination.getCity_longitude());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean updateDestination(Destination destination) {
        String query = "UPDATE Destination SET city_name=?, city_latitude=?, city_longitude=? WHERE cid=?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, destination.getCity_name());
            statement.setDouble(2, destination.getCity_latitude());
            statement.setDouble(3, destination.getCity_longitude());
            statement.setInt(4, destination.getCid());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean deleteDestination(int id) {
        String query = "DELETE FROM Destination WHERE cid=?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
