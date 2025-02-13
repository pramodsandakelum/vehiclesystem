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
import model.User;

/**
 *
 * @author pramo
 */
public class UserBL {
    
    public DBHandler dbManager = DBHandler.getInstance();
    public Connection dbConnection = dbManager.getConnection();
    
    public List<User> getallUser(){
        List<User> userlist = new ArrayList<>();
        try {
            String query = "SELECT * FROM Users";
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {                
                User user = new User();
                user.setUid(result.getInt("uid"));
                user.setUsername(result.getString("username"));
                user.setRole(result.getInt("role"));
                user.setFname(result.getString("fname"));
                user.setLname(result.getString("lname"));
                user.setEmail(result.getString("email"));
                user.setTelephone(result.getString("telephone"));
                user.setAddress(result.getString("address"));
                userlist.add(user);
            }
            return userlist;
            
            
        } catch (SQLException e) {
            return null;
        }
        
    }
    
    public List<User> getUserbyID(int id){
        List<User> singleUser = new ArrayList<>();
        try {
            String query = "SELECT * FROM Users WHERE uid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
           ResultSet result = statement.executeQuery(query);
            while (result.next()) {                
                User user = new User();
                user.setUid(result.getInt("uid"));
                user.setUsername(result.getString("username"));
                user.setRole(result.getInt("role"));
                user.setFname(result.getString("fname"));
                user.setLname(result.getString("lname"));
                user.setEmail(result.getString("email"));
                user.setTelephone(result.getString("telephone"));
                user.setAddress(result.getString("address"));
                singleUser.add(user);
            }
            return singleUser;
            
        } catch (SQLException e) {
            return null;
        }
    }
    
    public String createUser(User user){
        String Message = "Error Creating Account";
        try {
            String query = "INSERT INTO Users (username, password, role, fname, lname, email, telephone, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole());
            statement.setString(4, user.getFname());
            statement.setString(5, user.getLname());
            statement.setString(6, user.getEmail());
            statement.setString(7, user.getTelephone());
            statement.setString(8, user.getAddress());
            int result = statement.executeUpdate();
            if(result >0){
                return Message = "Account Created Successfully";
            }
                return Message;
        } catch (SQLException e) {
            return Message = "Internal Server Error" + e.getMessage();
        }
    }
    
    public String updateUser(User user){
        String Message = "Error Updating Account";
        try {
            String query = "UPDATE Users SET username=?, password = ?, role = ?, fname = ?, lname = ?, email = ?, telephone = ?, address = ? WHERE uid = ?;";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole());
            statement.setString(4, user.getFname());
            statement.setString(5, user.getLname());
            statement.setString(6, user.getEmail());
            statement.setString(7, user.getTelephone());
            statement.setString(8, user.getAddress());
            statement.setInt(9, user.getUid());
            int result = statement.executeUpdate();
            if(result >0){
                return Message = "Account Updated Successfully";
            }
                return Message;
        } catch (SQLException e) {
            return Message = "Internal Server Error" + e.getMessage();
        }
    }
    
    public String deleteUser(int id){
        String Message = "Error Deleting Account";
        try {
            String query = "DELETE FROM Users WHERE uid = ?;";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if(result >0){
                return Message = "Account Deleted Successfully";
            }
                return Message;
        } catch (SQLException e) {
            return Message = "Internal Server Error" + e.getMessage();
        }
    }
    
}
