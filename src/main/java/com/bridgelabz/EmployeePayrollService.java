package com.bridgelabz;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeePayrollService {
    private List<EmployeePayrollData> employeePayrollDataList;
    private EmployeePayrollDBService employeePayrollDBService = null;

    public EmployeePayrollService() {
        this.employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollData) {
        this();
        this.employeePayrollDataList = employeePayrollData;
    }

    /**
     * Creating readEmployeePayrollData from employeePayrollDBService
     * @return - employeePayrollDataList
     */
    public List<EmployeePayrollData> readEmployeePayrollData() {
        this.employeePayrollDataList = this.employeePayrollDBService.readEmployeePayrollData();
        return this.employeePayrollDataList;
    }

    /**
     * Creating updateEmployeeSalary to update the employee salary for given name in the list
     * @param name - name of the employee
     * @param salary - salary of the employee
     */
    public void updateEmployeeSalary(String name, double salary) {
        int result = this.employeePayrollDBService.updateEmployeeSalary(name, salary);
        if (result == 0) return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null) employeePayrollData.setSalary(salary);
    }

    /**
     * Creating getEmployeePayrollData to get the employee payroll date from the list
     * @param name - name of the employee
     * @return = employeePayrollData
     */
    private EmployeePayrollData getEmployeePayrollData(String name) {
        EmployeePayrollData employeePayrollData = this.employeePayrollDataList.stream()
                                                  .filter(employeePayrollData1 -> employeePayrollData1.getName()
                                                  .equalsIgnoreCase(name))
                                                  .findFirst()
                                                  .orElse(null);
        return employeePayrollData;
    }

    /**
     * Creating checkEmployeePayrollInSyncWithDB to check the employee name in list is match
     * with the employee name in DB
     * @param name - name of the employee
     * @return - true or false
     */
    public boolean checkEmployeePayrollInSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = this.employeePayrollDBService.readEmployeeData(name);
        return employeePayrollDataList.get(0).equals(this.getEmployeePayrollData(name));
    }

    /**
     * Creating readEmployeePayrollForDateRange method to read the employee details for the given date range
     * @param startDate - start date of the employee
     * @param endDate - today's date
     * @return - employeePayrollList
     */
    public List<EmployeePayrollData> readEmployeePayrollForDateRange(LocalDate startDate, LocalDate endDate) {
        List<EmployeePayrollData> employeePayrollList = this.employeePayrollDBService.readEmployeePayrollForDateRange(startDate, endDate);
        return employeePayrollList;
    }

    /**
     * Creating readAverageSalaryByGender to read average salary by gender from the map
     * @return - genderAndAverageSalaryMap
     */
    public Map<String, Double> readAverageSalaryByGender() {
        return employeePayrollDBService.getAverageSalaryByGender();
    }

    /**
     * Creating addEmployeeToPayroll method to call addEmployeeToPayroll from EmployeePayrollDBService
     * @param name
     * @param gender
     * @param salary
     * @param phone_number
     * @param address
     * @param department
     * @param start
     */
    public void addEmployeeToPayroll(String name, String gender, double salary, long phone_number,
                                     String address, String department, LocalDate start) {
        employeePayrollDBService.addEmployeeToPayroll(name, gender, salary, phone_number, address, department, start);
    }
}
