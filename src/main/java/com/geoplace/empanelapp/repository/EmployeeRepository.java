package com.geoplace.empanelapp.repository;

import com.geoplace.empanelapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Employee entities.
 * Provides CRUD operations and custom queries for Employee entities.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Finds all employees by department ID.
     *
     * @param departmentId the department ID
     * @return the list of employees in the specified department
     */
    List<Employee> findByDepartmentId(Long departmentId);
}
