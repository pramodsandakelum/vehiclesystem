/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        } catch (Exception e) {
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
        } catch (Exception e) {
            return null;
        }
    }
    
}
