/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author pramo
 */
public class DBHandler {

    private static DBHandler instance;
    private static Connection connection;

    private static String JDBC_URL = "jdbc:sqlserver://N7\\SQLEXPRESS2022;databaseName=megacitycabs;encrypt=true;trustServerCertificate=true;";
    private static String USER = "sa";
    private static String PASS = "1234";

    public DBHandler() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Log"+e.getMessage());
        }
    }
    
    public static DBHandler getInstance() {
        if (instance == null) {
            synchronized (DBHandler.class) {
                if (instance == null) {
                    instance = new DBHandler();
                }
            }
        }
        return instance;
    }
    
    public Connection getConnection(){
            return connection;
    }
    
    public void closeConnection(){
    
        try {
            connection.close();
        } catch (SQLException ex) {
            
        }
    }
}
