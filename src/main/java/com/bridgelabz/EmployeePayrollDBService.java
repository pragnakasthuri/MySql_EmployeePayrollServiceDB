package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {

    private static final String UPDATE_EMP_SAL_QRY = "UPDATE EMPLOYEE_PAYROLL SET SALARY = ? WHERE NAME = ?";
    private static final String GET_EMPLOYEE_QRY_PS = "SELECT * FROM EMPLOYEE_PAYROLL WHERE NAME = ?";
    private PreparedStatement preparedStatement = null;
    private PreparedStatement updatePrepareStatement = null;
    private static EmployeePayrollDBService employeePayrollDBService = null;

    private EmployeePayrollDBService() {}

    public static EmployeePayrollDBService getInstance() {
        if (employeePayrollDBService == null) {
            employeePayrollDBService = new EmployeePayrollDBService();
        }
        return employeePayrollDBService;
    }

    private void prepareStatementForEmployeeData() {
        try{
            Connection connection = this.getConnection();
            this.preparedStatement = connection.prepareStatement(GET_EMPLOYEE_QRY_PS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * main method for modification and execution of queries
     * @param args - default java param
     */
    public static void main(String[] args) {
        EmployeePayrollDBService service = new EmployeePayrollDBService();
        service.readEmployeePayrollData();
    }

    /**
     * Creating readData method to read data from employee_payroll DB
     * @return employeePayrollList
     */
    public List<EmployeePayrollData> readEmployeePayrollData() {
        String sql = "SELECT * FROM employee_Payroll";
        List<EmployeePayrollData> employeePayrollList = new ArrayList();
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                employeePayrollList = processResultSet(resultSet);
            }
        } catch(java.sql.SQLException e) {
                e.printStackTrace();
            }
        return employeePayrollList;
    }

    public List<EmployeePayrollData> readEmployeeData(String name) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList();
        if (this.preparedStatement == null) {
            this.prepareStatementForEmployeeData();
        }
        try {
            this.preparedStatement.setString(1, name);
            ResultSet rs = this.preparedStatement.executeQuery();
            employeePayrollList = this.processResultSet(rs);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    private List<EmployeePayrollData> processResultSet(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                double salary = resultSet.getDouble("salary");
                Long phoneNumber = resultSet.getLong("phone_number");
                String address = resultSet.getString("address");
                String department = resultSet.getString("department");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id, name, gender, salary, phoneNumber,
                        address, department, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
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

    public int updateEmployeeSalary(String name, double salary) {
        if (this.updatePrepareStatement == null) {
            this.prepareStatementToUpdateEmployeeSalary();
        }
        try {
            this.updatePrepareStatement.setDouble(1, salary);
            this.updatePrepareStatement.setString(2,name);
            return this.updatePrepareStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void prepareStatementToUpdateEmployeeSalary() {
        try{
            Connection connection = this.getConnection();
            this.updatePrepareStatement = connection.prepareStatement(UPDATE_EMP_SAL_QRY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
