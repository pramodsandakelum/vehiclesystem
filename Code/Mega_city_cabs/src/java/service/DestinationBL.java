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
import model.Destination;

/**
 *
 * @author pramo
 */
public class DestinationBL {
    
    public DBHandler dbManager = DBHandler.getInstance();
    public Connection dbConnection = dbManager.getConnection();
    
    public String returnPickup(int id){
        String pickup = "";
        try {
            String query = "SELECT city_name from Destination WHERE cid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {                
                pickup = result.getString("city_name");
            }
            return pickup;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public String returnDrop(int id){
        String drop = "";
        try {
            String query = "SELECT city_name from Destination WHERE cid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {                
                drop = result.getString("city_name");
            }
            return drop;
        } catch (SQLException e) {
            return null;
        }
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
            return locations;
        } catch (Exception e) {
            return null;
        }

    }
}
