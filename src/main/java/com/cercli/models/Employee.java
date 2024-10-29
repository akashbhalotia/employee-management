package com.cercli.models;

import java.util.UUID;
import java.time.ZonedDateTime;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import com.cercli.utils.TimeZoneUtils;
import com.cercli.converters.CurrencyConverter;
import com.cercli.converters.LocaleConverter;

/**
 * Represents an Employee entity with internationalization support.
 */
@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    @Column(name = "EmployeeId", columnDefinition = "BINARY(16)")
    private UUID employeeId;

    @NotNull
    @NotEmpty
    @Column(name = "Name", nullable = false)
    private String name;

    @NotNull
    @NotEmpty
    @Column(name = "Position", nullable = false)
    private String position;

    @NotNull
    @NotEmpty
    @Email(message = "Please provide a valid email address.")
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @NotNull
    @PositiveOrZero(message = "Salary must be zero or positive")
    @Column(name = "Salary", nullable = false)
    private BigDecimal salary;

    @NotNull
    @Column(name = "Currency", nullable = false)
    @Convert(converter = CurrencyConverter.class)
    private Currency currency;

    @Column(name = "Locale")
    @Convert(converter = LocaleConverter.class)
    private Locale locale;

    @Column(name = "CreatedAt", updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "ModifiedAt")
    private ZonedDateTime modifiedAt;

    /**
     * Default constructor required by JPA.
     */
    public Employee() {
        // Default constructor for JPA
    }

    /**
     * Constructs a new Employee with the specified details.
     *
     * @param name     the name of the employee
     * @param position the job position of the employee
     * @param email    the email address of the employee
     * @param salary   the salary of the employee
     * @param currency the currency of the salary
     * @param locale   the locale of the employee
     */
    public Employee(String name, String position, String email, BigDecimal salary, Currency currency, Locale locale) {
        this.employeeId = UUID.randomUUID();
        this.name = name;
        this.position = position;
        this.email = email;
        this.salary = salary;
        this.currency = currency;
        this.locale = locale;
    }

    // Getters and Setters

    public UUID getEmployeeId() {
        return employeeId;
    }

    // No setter for employeeId since it's generated

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    // Lifecycle Callbacks

    /**
     * Sets the creation and modification timestamps before persisting.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = TimeZoneUtils.nowInServerTime();
        modifiedAt = createdAt;
    }

    /**
     * Updates the modification timestamp before updating.
     */
    @PreUpdate
    protected void onUpdate() {
        modifiedAt = TimeZoneUtils.nowInServerTime();
    }

    // String Representation

    /**
     * Returns a string representation of the employee.
     *
     * @return a string containing employee details
     */
    @Override
    public String toString() {
        return "Employee {" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", currency=" + currency +
                ", locale=" + locale +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
