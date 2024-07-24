package com.geoplace.empanelapp.service;

import com.geoplace.empanelapp.dto.EmployeeDTO;
import com.geoplace.empanelapp.entity.Employee;
import com.geoplace.empanelapp.exception.ResourceNotFoundException;
import com.geoplace.empanelapp.repository.DepartmentRepository;
import com.geoplace.empanelapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for Employee-related operations.
 */
@Service
public class EmployeeServiceImpl  implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        if (employeeDTO.getDepartmentId() != null) {
            departmentRepository.findById(employeeDTO.getDepartmentId())
                    .ifPresent(employee::setDepartment);
        }
        Employee savedEmployee = employeeRepository.save(employee);
        return convertEmployeeEntityToDTO(savedEmployee);
    }

    @Override
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::convertEmployeeEntityToDTO);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertEmployeeEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::convertEmployeeEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(employeeDTO.getName());
                    employee.setSkills(employeeDTO.getSkills());
                    if (employeeDTO.getDepartmentId() != null) {
                        departmentRepository.findById(employeeDTO.getDepartmentId())
                                .ifPresent(employee::setDepartment);
                    }
                    Employee updatedEmployee = employeeRepository.save(employee);
                    return convertToDTO(updatedEmployee);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSkills(employee.getSkills());
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
        }
        return dto;
    }

    private EmployeeDTO convertEmployeeEntityToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSkills(employee.getSkills());
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
        }
        return dto;
    }
}
