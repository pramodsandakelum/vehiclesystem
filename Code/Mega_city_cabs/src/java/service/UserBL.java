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
import model.Driver;
import model.User;
import model.userCredentialDTO;

/**
 *
 * @author pramo
 */
public class UserBL {

    public DBHandler dbManager = DBHandler.getInstance();
    public Connection dbConnection = dbManager.getConnection();

    public List<User> getallUser() {
        List<User> user = new ArrayList<>();
        try {
            String query = "SELECT * FROM Users";
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                User userlist = new User();
                userlist.setUid(result.getInt("uid"));
                userlist.setUsername(result.getString("username"));
                userlist.setPassword(result.getString("password"));
                userlist.setRole(result.getInt("role"));
                if(result.getInt("role") == 2){
                Driver driver = returnDriverProfile(result.getInt("uid"));
                if(driver.isIs_available() == false){
                    userlist.setBooked(true);
                }                
                }
                userlist.setFname(result.getString("fname"));
                userlist.setLname(result.getString("lname"));
                userlist.setEmail(result.getString("email"));
                userlist.setTelephone(result.getString("telephone"));
                userlist.setAddress(result.getString("address"));
                user.add(userlist);
            }
            return user;

        } catch (SQLException e) {
            
            return null;
        }

    }
    
    public userCredentialDTO userLogin(String username,String password){
        userCredentialDTO udt = new userCredentialDTO();
        try {
            String query = "SELECT * from Users WHERE username=? AND password=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                udt.setUsername(rs.getString("username"));
                udt.setRole(rs.getInt("role"));
                udt.setUid(rs.getInt("uid"));
                return udt;
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public String returnUserName(int id) {
        String fullname = null;
        try {
            String query = "SELECT fname, lname from Users WHERE uid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                fullname = result.getString("fname")+" "+result.getString("lname");
            }
            return fullname;
        } catch (SQLException e) {
            return fullname;
        }
    }

    public User getUserbyID(int id) {
        User user = new User();
        try {
            String query = "SELECT * FROM Users WHERE uid=?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                user.setUid(result.getInt("uid"));
                user.setUsername(result.getString("username"));
                user.setRole(result.getInt("role"));
                user.setFname(result.getString("fname"));
                user.setLname(result.getString("lname"));
                user.setEmail(result.getString("email"));
                user.setTelephone(result.getString("telephone"));
                user.setAddress(result.getString("address"));
            }
            return user;

        } catch (SQLException e) {
            return null;
        }
    }

    public String createUser(User user) {
    String message = "Error Creating Account";

    String query = "INSERT INTO Users (username, password, role, fname, lname, email, telephone, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY();";

    try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setInt(3, user.getRole()); // Roles: admin=1, driver=2, customer=3
        statement.setString(4, user.getFname());
        statement.setString(5, user.getLname());
        statement.setString(6, user.getEmail());
        statement.setString(7, user.getTelephone());
        statement.setString(8, user.getAddress());
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            int lastID = rs.getInt(1); // Get the last inserted ID
            // If role is Driver (role = 2), create driver profile
            if (user.getRole() == 2) {
                Driver driver = new Driver();
                driver.setId(lastID);
                driver.setName(user.getFname() + " " + user.getLname());
                driver.setIs_available(true);
                System.out.println("driver create");
                createDriverProfile(driver);
            }

            return "Account Created Successfully";
        }
    } catch (SQLException e) {
        return "Internal Server Error: " + e.getMessage();
    }

    return message;
}


    public String updateUser(User user) {
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
            if (result > 0) {
                User existingUser = getUserbyID(user.getUid());
                if (existingUser != null && existingUser.getRole() == 2) {
                    Driver driver = new Driver();
                    driver.setId(user.getUid());
                    driver.setName(user.getFname() + " " + user.getLname());
                    driver.setIs_available(true);
                    // search if a driver exists before update else create a new one by passing same data
                    int exists = searchDriverProfile(user.getUid());
                    if(exists >0){
                        System.out.println("driver update");
                    updateDriverProfile(driver);
                    }else{
                        System.out.println("driver create");
                        createDriverProfile(driver);
                    }
                    
                    
                    
                }else{    
                    System.out.println("driver delete");
                        deleteDriverProfile(user.getUid());              
                }
                Message = "Account Updated Successfully";
            }
            return Message;
        } catch (SQLException e) {
            return "Internal Server Error" + e.getMessage();
        }
    }

    public String deleteUser(int id) {
        String Message = "Error Deleting Account";
        try {
            User u = new User();
            u = getUserbyID(id);
            if (u.getRole() == 2) {
                deleteDriverProfile(id);
            }

            String query = "DELETE FROM Users WHERE uid = ?;";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result > 0) {

                Message = "Account Deleted Successfully";
            }
            return Message;
        } catch (SQLException e) {
            return "Internal Server Error" + e.getMessage();
        }
    }

    public void createDriverProfile(Driver driver) {
        try {
            String query = "INSERT INTO Driver (id, name, is_available) VALUES (?, ?, ?);";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, driver.getId());
            statement.setString(2, driver.getName());
            statement.setBoolean(3, true);
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void updateDriverProfile(Driver driver) {
        String query = "UPDATE Driver SET name = ?, is_available = ? WHERE id = ?;";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, driver.getName());
            statement.setBoolean(2, driver.isIs_available());
            statement.setInt(3, driver.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void deleteDriverProfile(int driverId) {
        String query = "DELETE FROM Driver WHERE id = ?;";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, driverId);
            statement.executeUpdate();
        } catch (SQLException e) {

        }

    }
    
    public boolean unbookDriver(int did) {
        try {
            String query = "UPDATE Driver SET is_available = true WHERE did = ?";
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, did);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int searchDriverProfile(int driverId) {
        String query = "SELECT * FROM Driver WHERE id = ?;";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, driverId);
           int result = statement.executeUpdate();
           return result;
        } catch (SQLException e) {
            
        }
        return 0;
    }
    
    public List<Driver> getallDriver() {
        List<Driver> driver = new ArrayList<>();
        try {
            String query = "SELECT * FROM Driver WHERE is_available=1";
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Driver driverlist = new Driver();
                driverlist.setId(result.getInt("id"));
                driverlist.setName(result.getString("name"));
                driverlist.setIs_available(result.getBoolean("is_available"));
                driver.add(driverlist);
            }
            return driver;

        } catch (SQLException e) {
            
            return null;
        }

    }
    
    
    public Driver returnDriverProfile(int driverId) {
        String query = "SELECT * FROM Driver WHERE id = ?;";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, driverId);
            ResultSet result = statement.executeQuery();
           if(result.next()){
               Driver driver = new Driver();
               driver.setId(result.getInt("id"));
               driver.setName(result.getString("name"));
               driver.setIs_available(result.getBoolean("is_available"));             
               return driver;
           }
           
        } catch (SQLException e) {
           return null;
        }
        return null;
    }

}
