package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class EmployeePayrollServiceTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
        Assertions.assertEquals(9,employeePayrollData.size());
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldNotMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
        Assertions.assertNotEquals(6,employeePayrollData.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
        employeePayrollService.updateEmployeeSalary("Terissa", 3000000);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terissa");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        LocalDate startDate = LocalDate.of(2019,01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollForDateRange(startDate, endDate);
        Assertions.assertEquals(8, employeePayrollData.size());
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldNotMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        LocalDate startDate = LocalDate.of(2019,01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollForDateRange(startDate, endDate);
        Assertions.assertNotEquals(3, employeePayrollData.size());
    }
}
