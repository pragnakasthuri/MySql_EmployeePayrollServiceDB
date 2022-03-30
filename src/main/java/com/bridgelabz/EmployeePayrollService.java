package com.bridgelabz;

import java.util.List;

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

    public List<EmployeePayrollData> readEmployeePayrollData() {
        this.employeePayrollDataList = this.employeePayrollDBService.readEmployeePayrollData();
        return this.employeePayrollDataList;
    }

    public void updateEmployeeSalary(String name, double salary) {
        int result = this.employeePayrollDBService.updateEmployeeSalary(name, salary);
        if (result == 0) return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null) employeePayrollData.setSalary(salary);
    }

    private EmployeePayrollData getEmployeePayrollData(String name) {
        EmployeePayrollData employeePayrollData = this.employeePayrollDataList
                .stream()
                .filter(employeePayrollData1 -> employeePayrollData1.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
        return employeePayrollData;
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = this.employeePayrollDBService.readEmployeeData(name);
        return employeePayrollDataList.get(0).equals(this.getEmployeePayrollData(name));
    }
}
