package com.bridgelabz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollServiceTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        List<EmployeePayrollData> employeePayrollData= employeePayrollDBService.readData();
        Assertions.assertEquals(9,employeePayrollData.size());
    }
}
