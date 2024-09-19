package com.thi.realestateplatformsprojectbe.services;
import com.thi.realestateplatformsprojectbe.dto.EmployeeDTO;
import com.thi.realestateplatformsprojectbe.models.Employee;

import java.util.Optional;

public interface IEmployeeService {
    Iterable<Employee> getAllEmployees();

    Employee findByAccountId(Long accountId);

    Optional<Employee> updateEmployee(Long id, EmployeeDTO employeeDTO);

    boolean deleteEmployee(Long id);

    Employee createEmployee(EmployeeDTO employeeDTO);

    Iterable<Employee> searchEmployees(String code, String name, String email, String position);

    Optional<Employee> getEmployeeById(Long id);

    boolean emailExists(String email);
}