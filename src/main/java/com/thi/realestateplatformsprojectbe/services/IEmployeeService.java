package com.thi.realestateplatformsprojectbe.services;
import com.thi.realestateplatformsprojectbe.dto.EmployeeDTO;
import com.thi.realestateplatformsprojectbe.models.Employee;

import java.util.Optional;

public interface IEmployeeService {
    Iterable<Employee> getAllEmployees();

    Employee findByAccountId(Long accountId);

    Optional<Employee> getEmployeeById(Long id);


    Employee createEmployee(EmployeeDTO employee);

    boolean deleteEmployee(Long id);


    Optional<Employee> updateEmployee(Long id, EmployeeDTO employeeDTO);

    Iterable<Employee> searchEmployees(String code, String name, String email, Long positionId);
}
