package com.geoplace.empanelapp.service;

import com.geoplace.empanelapp.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Employee-related operations.
 */
public interface EmployeeService {

    /**
     * Creates a new employee.
     *
     * @param employeeDTO the employee DTO
     * @return the created employee DTO
     */
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    /**
     * Retrieves an employee by ID.
     *
     * @param id the employee ID
     * @return the employee DTO
     */
    Optional<EmployeeDTO> getEmployeeById(Long id);

    /**
     * Retrieves all employees.
     *
     * @return the list of employee DTOs
     */
    List<EmployeeDTO> getAllEmployees();

    /**
     * Retrieves employees by department ID.
     *
     * @param departmentId the department ID
     * @return the list of employee DTOs
     */
    List<EmployeeDTO> getEmployeesByDepartment(Long departmentId);

    /**
     * Updates an existing employee.
     *
     * @param id the employee ID
     * @param employeeDTO the employee DTO
     * @return the updated employee DTO
     */
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    /**
     * Deletes an employee by ID.
     *
     * @param id the employee ID
     */
    void deleteEmployee(Long id);
}