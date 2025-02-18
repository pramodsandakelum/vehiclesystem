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
                userlist.setRole(result.getInt("role"));
                userlist.setFname(result.getString("fname"));
                userlist.setLname(result.getString("lname"));
                userlist.setEmail(result.getString("email"));
                userlist.setTelephone(result.getString("telephone"));
                userlist.setAddress(result.getString("address"));
                user.add(userlist);
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
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

        String query = "INSERT INTO Users (username, password, role, fname, lname, email, telephone, address) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY();";

        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole()); // Roles: admin=1, driver=2, customer=3
            statement.setString(4, user.getFname());
            statement.setString(5, user.getLname());
            statement.setString(6, user.getEmail());
            statement.setString(7, user.getTelephone());
            statement.setString(8, user.getAddress());

            // Execute INSERT + SCOPE_IDENTITY()
            boolean result = statement.execute(); // Executes the statement

            if (result) {
                try (ResultSet rs = statement.getResultSet()) {
                    if (rs.next()) {
                        int lastID = rs.getInt(1); // Get the last inserted ID

                        // If role is Driver (role = 2), create driver profile
                        if (user.getRole() == 2) {
                            Driver driver = new Driver();
                            driver.setId(lastID);
                            driver.setName(user.getFname() + " " + user.getLname());
                            driver.setIs_available(true);
                            createDriverProfile(driver);
                        }

                        return "Account Created Successfully";
                    }
                }
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
                    updateDriverProfile(driver);
                }
                return Message = "Account Updated Successfully";
            }
            return Message;
        } catch (SQLException e) {
            return Message = "Internal Server Error" + e.getMessage();
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

                return Message = "Account Deleted Successfully";
            }
            return Message;
        } catch (SQLException e) {
            return Message = "Internal Server Error" + e.getMessage();
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

}
