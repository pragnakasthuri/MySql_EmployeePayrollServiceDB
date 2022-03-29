package com.bridgelabz;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class DBDemo {
    /**
     * Main method for creating database connection
     * @param args - default java param
     */
    public static void main(String[] args) {
        /**
         * Initializing the jdbc url, username and password
         */
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "EarthM@1416";
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver",e);
        }
        listDrivers();

        try {
            System.out.println("Connecting to database: "+jdbcURL);
            /**
             * Establishing the connection
             */
            connection = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection is Successful");
        } catch (java.sql.SQLException e){
            e.getMessage();
        }
    }

    /**
     * Creating the listDrivers method to list the drivers
     */
    public static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println("Drivers :" +driverClass.getClass().getName());
        }
    }
}
