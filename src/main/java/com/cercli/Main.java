package com.cercli;

import com.cercli.dao.EmployeeDAO;
import com.cercli.dependencies.PersistenceModule;
import com.cercli.models.Employee;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new PersistenceModule());

        EmployeeDAO employeeDAO = injector.getInstance(EmployeeDAO.class);

        // Add a new employee with valid data
        Employee newEmployee = new Employee(
                "John Doe",
                "Software Engineer",
                "john.doe@example.com",
                new BigDecimal("75000"),
                Currency.getInstance("USD"),
                Locale.US
        );

        try {
            employeeDAO.addEmployee(newEmployee);
            System.out.println("Added Employee: " + newEmployee.getName());
        } catch (Exception e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }

        // Get an employee
        Employee employee = employeeDAO.getEmployee(newEmployee.getEmployeeId());
        if (employee != null) {
            printEmployeeDetails(employee);
        } else {
            System.out.println("Employee not found.");
        }

        // Get all employees
        List<Employee> employees = employeeDAO.getAllEmployees();
        System.out.println("Total Employees: " + employees.size());

        // Update an employee
        if (employee != null) {
            employee.setName("Jane Doe");
            employee.setEmail("jane.doe@example.com");
            employee.setPosition("Senior Software Engineer");
            employee.setSalary(new BigDecimal("85000"));
            try {
                employeeDAO.updateEmployee(employee);
                System.out.println("Updated Employee: " + employee.getName());
            } catch (Exception e) {
                System.err.println("Error updating employee: " + e.getMessage());
            }

            // Delete an employee
            try {
                employeeDAO.deleteEmployee(employee.getEmployeeId());
                System.out.println("Deleted Employee with ID: " + employee.getEmployeeId());
            } catch (Exception e) {
                System.err.println("Error deleting employee: " + e.getMessage());
            }
        } else {
            System.out.println("Employee not found. Skipping update and delete operations.");
        }
    }

    /**
     * Prints the details of an employee to the console.
     *
     * @param employee the employee whose details are to be printed
     */
    private static void printEmployeeDetails(Employee employee) {
        System.out.println("Employee Details:");
        System.out.println(employee);
    }
}
