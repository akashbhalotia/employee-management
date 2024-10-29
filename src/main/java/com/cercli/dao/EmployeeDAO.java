package com.cercli.dao;

import com.cercli.models.Employee;
import com.cercli.utils.TimeZoneUtils;
import com.google.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing Employee entities.
 * Provides methods for CRUD operations.
 */
public class EmployeeDAO {

    private final EntityManager em;

    @Inject
    public EmployeeDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Adds a new employee to the database.
     *
     * @param employee the employee to add
     * @throws ConstraintViolationException if validation fails
     * @throws PersistenceException         if a database error occurs
     */
    public void addEmployee(Employee employee) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(employee);
            tx.commit();
        } catch (ConstraintViolationException e) {
            tx.rollback();
            System.err.println("Validation error(s):");
            e.getConstraintViolations().forEach(violation -> {
                System.err.println("- Property: " + violation.getPropertyPath()
                        + ", Invalid Value: " + violation.getInvalidValue()
                        + ", Error Message: " + violation.getMessage());
            });
            throw e;
        } catch (PersistenceException e) {
            tx.rollback();
            System.err.println("Database error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param employeeId the UUID of the employee
     * @return the Employee object if found, or null
     */
    public Employee getEmployee(UUID employeeId) {
        Employee employee = em.find(Employee.class, employeeId);
        if (employee != null) {
            employee.setCreatedAt(TimeZoneUtils.toLocalTime(employee.getCreatedAt()));
            employee.setModifiedAt(TimeZoneUtils.toLocalTime(employee.getModifiedAt()));
        }
        return employee;
    }

    /**
     * Retrieves all employees from the database.
     *
     * @return a list of Employee objects
     */
    public List<Employee> getAllEmployees() {
        TypedQuery<Employee> query = em.createQuery("FROM Employee", Employee.class);
        List<Employee> employees = query.getResultList();
        for (Employee employee : employees) {
            employee.setCreatedAt(TimeZoneUtils.toLocalTime(employee.getCreatedAt()));
            employee.setModifiedAt(TimeZoneUtils.toLocalTime(employee.getModifiedAt()));
        }
        return employees;
    }

    /**
     * Updates an existing employee in the database.
     *
     * @param employee the employee to update
     * @throws ConstraintViolationException if validation fails
     * @throws PersistenceException         if a database error occurs
     */
    public void updateEmployee(Employee employee) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(employee);
            tx.commit();
        } catch (ConstraintViolationException e) {
            tx.rollback();
            System.err.println("Validation error(s) during update:");
            e.getConstraintViolations().forEach(violation -> {
                System.err.println("- Property: " + violation.getPropertyPath()
                        + ", Invalid Value: " + violation.getInvalidValue()
                        + ", Error Message: " + violation.getMessage());
            });
            throw e;
        } catch (PersistenceException e) {
            tx.rollback();
            System.err.println("Database error during update: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId the UUID of the employee to delete
     * @throws PersistenceException if a database error occurs
     */
    public void deleteEmployee(UUID employeeId) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employee employee = em.find(Employee.class, employeeId);
            if (employee != null) {
                em.remove(employee);
                tx.commit();
                System.out.println("Employee deleted successfully.");
            } else {
                tx.rollback();
                System.err.println("Employee not found for deletion.");
            }
        } catch (PersistenceException e) {
            tx.rollback();
            System.err.println("Database error during deletion: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Returns a string representation of all the employees in the database.
     *
     * @return a string containing all the employee details
     */
    @Override
    public String toString() {
        List<Employee> employees = getAllEmployees();
        StringBuilder sb = new StringBuilder();
        sb.append("Employees in the Database:\n");
        for (Employee employee : employees) {
            sb.append(employee.toString()).append("\n");
        }
        return sb.toString();
    }
}
