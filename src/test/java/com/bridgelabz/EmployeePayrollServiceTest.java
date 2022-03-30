package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollServiceTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollDBService employeePayrollDBService = EmployeePayrollDBService.getInstance();
        List<EmployeePayrollData> employeePayrollData= employeePayrollDBService.readData();
        Assertions.assertEquals(9,employeePayrollData.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
        employeePayrollService.updateEmployeeSalary("Terissa", 3000000);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terissa");
        Assertions.assertTrue(result);
    }
}
