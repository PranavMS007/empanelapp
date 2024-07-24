package com.geoplace.empanelapp.repository;

import com.geoplace.empanelapp.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Department entities.
 * Provides CRUD operations for Department entities.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}