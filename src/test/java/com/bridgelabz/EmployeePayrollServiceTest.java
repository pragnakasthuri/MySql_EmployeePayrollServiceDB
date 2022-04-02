package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeePayrollServiceTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
        Assertions.assertEquals(9, employeePayrollData.size());
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldNotMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
        Assertions.assertNotEquals(6, employeePayrollData.size());
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
        LocalDate startDate = LocalDate.of(2019, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollForDateRange(startDate, endDate);
        Assertions.assertEquals(9, employeePayrollData.size());
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldNotMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        LocalDate startDate = LocalDate.of(2019, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollForDateRange(startDate, endDate);
        Assertions.assertNotEquals(3, employeePayrollData.size());
    }

    @Test
    public void givenPayrollData_WhenAvgSalaryRetrieved_ShouldMatchWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender();
        Assertions.assertTrue(averageSalaryByGender.get("M").equals(1880000.0) &&
                averageSalaryByGender.get("F").equals(2050000.0));
    }

    @Test
    public void givenPayrollData_WhenAvgSalaryRetrieved_ShouldNotMatchWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender();
        Assertions.assertFalse(averageSalaryByGender.get("M").equals(1800000.0) &&
                averageSalaryByGender.get("F").equals(2000000.0));
    }

    @Test
    public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData();
        employeePayrollService.addEmployeeToPayroll(12, "Gene", "F", 2500000.0, 9876543977L, "hyderabad", "sales", LocalDate.now());
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Gene");
        Assertions.assertTrue(result);
    }
}


