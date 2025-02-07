/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pramo
 */
public class User {
    
    private String username;
    private String password;
    private int role; // 1 = Admin, 2 = Driver, 3 = Customer
    
    public User(String username,String password,int role){ 
        this.username = username;
        this.password = password;
        this.role = role;
    } // constructor to pass all values at once

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    
    
    
}
