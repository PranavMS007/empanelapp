# Employee Management App

The Employee Management App is a Spring Boot application that provides RESTful APIs for managing employee and department data, including CRUD operations and querying by department.

## Table of Contents

- [Overview](#overview)
- [Running the Project](#running-the-project)
- [Accessing the H2 Console](#accessing-the-h2-console)
- [API Endpoints](#api-endpoints)
  - [GET /api/employees](#get-employees)
  - [GET /api/employees/{id}](#get-employeesid)
  - [GET /api/employees/department/{departmentId}](#get-employeesdepartmentdepartmentid)
  - [POST /api/employees](#post-employees)
  - [PUT /api/employees/{id}](#put-employeesid)
  - [DELETE /api/employees/{id}](#delete-employeesid)
  - [Conclusion.](#Conclusion)

## Overview

The Employee Management App is a Spring Boot application designed to manage employees and departments. It provides RESTful APIs to create, read, update, and delete employee records.

## Running the Project

**Clone the Repository**

   ```sh
   git clone https://github.com/PranavMS007/empanelapp.git
   
   cd employee-management-app
```
Use Maven to build and run the application:
```sh
mvn spring-boot:run
```

his will start the Spring Boot application on 
```sh
http://localhost:8080
```
## Accessing the H2 Console
The H2 database console can be accessed at `http://localhost:8080/h2-console`. Use the following credentials:

JDBC URL: `jdbc:h2:mem:testdb`

Username: `sa`

Password: `password`

## API Endpoints

**1. GET /api/employees**

   Retrieve a list of all employees.


  **1. GET /api/employees**

   Retrieve details of an employee by ID.   
  
**2. GET /employees/{id}**

  Retrieve details of an employee by ID.


**3. GET /api/employees/department/{departmentId}**
  
  Retrieve a list of employees in a specific department.

**4. POST /api/employees/add**

Create a new employee.

   
**5. PUT /api/employees/{id}**
  
  Update an existing employee's details.
  
 
**6. DELETE /api/employees/{id}**

Delete an employee by ID.

## Conclusion

This README provides an overview of the Employee Management App(**EMPanel**), including instructions for running the project, accessing the H2 console, and using the available API endpoints. For any questions or issues, please refer to the project's issue tracker on GitHub.
