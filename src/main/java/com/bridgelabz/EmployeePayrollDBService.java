package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
    /**
     * main method for modification and execution of queries
     * @param args - default java param
     */
    public static void main(String[] args) {
        EmployeePayrollDBService service = new EmployeePayrollDBService();
        service.readData();
    }

    /**
     * Creating readData method to read data from employee_payroll DB
     * @return employeePayrollList
     */
    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_Payroll";
        List<EmployeePayrollData> employeePayrollList = new ArrayList();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                Long phoneNumber = resultSet.getLong("phone_number");
                String address = resultSet.getString("address");
                String department =  resultSet.getString("department");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, gender, phoneNumber,
                                        address, department, startDate));
            }
        } catch(java.sql.SQLException e) {
                e.printStackTrace();;
            }
        return employeePayrollList;
    }

    /**
     * Creating getConnection to establish the connection between java and Mysql DB through JDBC
     * @return - connection
     */
    private Connection getConnection() {
        /**
         * Initializing the jdbc url, username and password
         */
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "EarthM@1416";
        Connection connection = null;
        try {
            /**
             * Load the Driver class
             */
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver",e);
        }

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
        return connection;
    }
}
