# Employee Management System

A Java-based Employee Management System that demonstrates CRUD operations with a focus on internationalization, data
validation, and proper coding standards. The application interacts with a MySQL database using Hibernate ORM and ensures
data integrity using Bean Validation (Jakarta Validation API).

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
    - [Clone the Repository](#clone-the-repository)
    - [Database Setup](#database-setup)
    - [Environment Variables](#environment-variables)
    - [Build and Run the Application](#build-and-run-the-application)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Internationalization Support](#internationalization-support)
- [Time Zone Simulation](#time-zone-simulation)
- [Data Validation](#data-validation)

## Features

- **CRUD Operations**: Create, Read, Update, Delete employees in the database.
- **Internationalization**: Supports multiple currencies and locales.
- **Data Validation**: Ensures valid email formats and non-negative salaries.
- **Time Zone Simulation**: Simulates a remote server in a different time zone.

## Technologies Used

- **Java 17**
- **Hibernate ORM 6.2.7.Final**
- **Hibernate Validator 8.0.1.Final**
- **Jakarta Persistence API**
- **Jakarta Validation API**
- **Jakarta Expression Language**
- **MySQL Database**
- **Google Guice for Dependency Injection**
- **SLF4J for Logging**
- **Maven for Build Management**

## Prerequisites

- **Java Development Kit (JDK) 17 or higher**
- **Apache Maven 3.8.0 or higher**
- **MySQL Server 5.7 or higher**
- **Git (optional, for cloning the repository)**

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/akashbhalotia/employee-management.git
cd employee-management
```

### Database Setup

1. Create a MySQL Database:

```bash
CREATE DATABASE employee_db;
```

2. Create a User (optional):

```bash
CREATE USER 'your_db_user'@'localhost' IDENTIFIED BY 'your_db_password';
GRANT ALL PRIVILEGES ON employee_db.* TO 'your_db_user'@'localhost';
FLUSH PRIVILEGES;
```

3. Update ```persistence.xml```:

Edit ```src/main/resources/META-INF/persistence.xml``` and update the database connection properties if necessary.

```bash
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/employee_db"/>
<property name="jakarta.persistence.jdbc.user" value="your_db_user"/>
<property name="jakarta.persistence.jdbc.password" value="your_db_password"/>
```

### Environment Variables

You can also set the database username and password using environment variables:

- ```DB_USERNAME```
- ```DB_PASSWORD```

### Build and Run the Application

1. Build the Project:

```bash
mvn clean install
```

2. Run the Application:

```bash
mvn exec:java -Dexec.mainClass="com.cercli.Main"
```

Alternatively, you can run the application from your IDE.

## Project Structure

```agsl
employee-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── cercli/
│   │   │           ├── Main.java
│   │   │           ├── dao/
│   │   │           │   └── EmployeeDAO.java
│   │   │           ├── dependencies/
│   │   │           │   └── PersistenceModule.java
│   │   │           ├── models/
│   │   │           │   └── Employee.java
│   │   │           ├── utils/
│   │   │           │   └── TimeZoneUtils.java
│   │   │           └── converters/
│   │   │               ├── CurrencyConverter.java
│   │   │               └── LocaleConverter.java
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml
├── pom.xml
└── README.md
```

## Usage

The application demonstrates basic CRUD operations on Employee entities. Upon running the application, it will:

1. Add a New Employee: Creates a new employee with valid data.
2. Retrieve the Employee: Fetches the employee from the database and prints the details.
3. Update the Employee: Updates the employee's information.
4. Delete the Employee: Removes the employee from the database.
5. Print All Employees: Displays all employees currently in the database.

Sample Output:

```agsl
Added Employee: John Doe
Employee Details:
Employee {employeeId=..., name='John Doe', position='Software Engineer', email='john.doe@example.com', salary=75000.00, currency=USD, locale=en_US, createdAt=2023-10-29T08:00:00Z, modifiedAt=2023-10-29T08:00:00Z}
Total Employees: 1
Updated Employee: Jane Doe
Deleted Employee with ID: ...
```

## Internationalization Support

- **Currency:** The Employee class includes a Currency field, allowing salaries to be represented in different
  currencies.
- **Locale:** A Locale field is included to support region-specific settings.
- **BigDecimal Salary:** Salaries are stored using BigDecimal for precise financial calculations.

## Time Zone Simulation

- The application simulates a remote server in the UTC time zone.
- ```TimeZoneUtils:``` A utility class that provides methods to get the current server time and convert server times to
  local times.
- **Created/Modified Dates:** When retrieving employees, the createdAt and modifiedAt fields are converted to the local
  time zone.

## Data Validation

- **Email Validation:** Ensures that the email provided is in a valid format using @Email annotation.
- **Salary Validation:** Ensures that the salary is zero or positive using @PositiveOrZero.
- **Exception Handling:** Validation errors are caught, and detailed messages are displayed in the console.

---

Note: This project is intended for educational purposes and demonstrates various Java programming concepts, including
internationalization, data validation, and database interactions using Hibernate. Feel free to use it as a reference or
a starting point for your own projects.

## Future Scope

- Improve documentation
- Logging
- Adding thorough tests
- Adding caching logic
- Instead of single DB server, integrate load balancing. One main, several replicas. Write to main, read from replicas.
- Improve console interaction logic (Main file).