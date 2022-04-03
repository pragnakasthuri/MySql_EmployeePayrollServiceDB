package com.bridgelabz;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePayrollDBService {
    /**
     * Initializing all the  sql queries
     */
    private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employee_Payroll;";
    private static final String UPDATE_EMP_SAL_BASED_ON_NAME_QRY = "UPDATE EMPLOYEE_PAYROLL SET SALARY = ? WHERE NAME = ?;";
    private static final String SELECT_EMPLOYEE_WHERE_NAME_QRY = "SELECT * FROM EMPLOYEE_PAYROLL WHERE NAME = ?;";
    private static final String SELECT_EMPLOYEES_WHERE_DATE_QRY = "SELECT * FROM employee_payroll WHERE start BETWEEN '%s' AND '%s';";
    private static final String SELECT_AVG_SALARY_GROUP_BY_GENDER = "SELECT GENDER, AVG(salary) as avg_salary FROM employee_payroll " +
                                                                     "GROUP BY gender;";
    private static final String INSERT_EMPLOYEE_QUERY = "INSERT INTO employee_payroll (name, gender, salary, phone_number, address, " +
                                                        "department, start) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');";
                                                        //name, gender, salary, phone_number, address, department, start;

    private PreparedStatement preparedStatement = null;
    private PreparedStatement updatePrepareStatement = null;

    private static EmployeePayrollDBService employeePayrollDBService = null;

    /**
     * Creating an EmployeePayrollDBService as private to make it singleton
     */
    private EmployeePayrollDBService() {
    }

    /**
     * Creating getInstance method to use this singleton object
     * @return employeePayrollDBService
     */
    public static EmployeePayrollDBService getInstance() {
        if (employeePayrollDBService == null) {
            employeePayrollDBService = new EmployeePayrollDBService();
        }
        return employeePayrollDBService;
    }

    /**
     * Creating prepareStatementForEmployeeData for executing prepared statement
     */
    private void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            this.preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_WHERE_NAME_QRY);
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
        service.readEmployeePayrollData(SELECT_ALL_EMPLOYEES);
    }

    /**
     * Creating readData public method to read data from employee_payroll DB
     * @return readEmployeePayrollData
     */
    public List<EmployeePayrollData> readEmployeePayrollData() {
        return readEmployeePayrollData(SELECT_ALL_EMPLOYEES);
    }

    /**
     * Creating readData private method to read data from employee_payroll DB
     * @return employeePayrollList
     */
    private List<EmployeePayrollData> readEmployeePayrollData(String query) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                employeePayrollList = processResultSet(resultSet);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    /**
     * This method is created to read an employee data based on given name
     * @param name
     * @return emp payroll data object in a list
     */
    public List<EmployeePayrollData> readEmployeeData(String name) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList();
        if (this.preparedStatement == null) {
            this.prepareStatementForEmployeeData();
        }
        try {
            this.preparedStatement.setString(1, name);
            ResultSet rs = this.preparedStatement.executeQuery();
            employeePayrollList = this.processResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    /**
     * This is a generic method to process the result set and create emp payroll list
     * @param resultSet
     * @return list of emp payroll data objects
     */
    private List<EmployeePayrollData> processResultSet(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        if (resultSet == null) {
            return employeePayrollDataList;
        }
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
     *
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
            throw new IllegalStateException("Cannot find the driver", e);
        }

        try {
            System.out.println("Connecting to database: " + jdbcURL);
            /**
             * Establishing the connection
             */
            connection = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection is Successful");
        } catch (java.sql.SQLException e) {
            e.getMessage();
        }
        return connection;
    }

    /**
     * Creating this method to update the employee salary for given name
     * @param name - name of the employee
     * @param salary - salary of the employee
     * @return - count of the updated employee for given salary and name
     */
    public int updateEmployeeSalary(String name, double salary) {
        if (this.updatePrepareStatement == null) {
            this.prepareStatementToUpdateEmployeeSalary();
        }
        try {
            this.updatePrepareStatement.setDouble(1, salary);
            this.updatePrepareStatement.setString(2, name);
            return this.updatePrepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Creating readEmployeePayrollForDateRange method to prepared statement
     */
    private void prepareStatementToUpdateEmployeeSalary() {
        try {
            Connection connection = this.getConnection();
            this.updatePrepareStatement = connection.prepareStatement(UPDATE_EMP_SAL_BASED_ON_NAME_QRY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creating readEmployeePayrollForDateRange method to read employee payroll for date range from DB
     * @param startDate - start date of the employee
     * @param endDate - today's date
     * @return - employeePayrollList
     */
    public List<EmployeePayrollData> readEmployeePayrollForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format(SELECT_EMPLOYEES_WHERE_DATE_QRY, Date.valueOf(startDate), Date.valueOf(endDate));
        return this.readEmployeePayrollData(sql);
    }

    /**
     * Creating getAverageSalaryByGender method to get the average salary by gender from DB
     * @return genderAndAverageSalaryMap
     */
    public Map<String, Double> getAverageSalaryByGender() {
        Map<String, Double> genderAndAverageSalaryMap = new HashMap<>();
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_AVG_SALARY_GROUP_BY_GENDER);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double salary = resultSet.getDouble("avg_salary");
                genderAndAverageSalaryMap.put(gender,salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderAndAverageSalaryMap;
    }

    public EmployeePayrollData addEmployeeToPayroll(String name, String gender, double salary, long phone_number,
                                     String address, String department, LocalDate start) {
        int empId = -1;
        EmployeePayrollData employeePayrollData = null;
        String sql = String.format(INSERT_EMPLOYEE_QUERY, name, gender, salary, phone_number, address, department, Date.valueOf(start));
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if(rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) empId = resultSet.getInt(1);
            }
            employeePayrollData = new EmployeePayrollData(empId, name, gender, salary, phone_number, address, department, start);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollData;
    }
}
