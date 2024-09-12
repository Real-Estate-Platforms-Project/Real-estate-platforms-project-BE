package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.Employee;
import com.thi.realestateplatformsprojectbe.repositories.IEmployeeRepository;
import com.thi.realestateplatformsprojectbe.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepository employeeRepository;

    @Override
    public Employee findByAccountId(Long accountId) {
        return employeeRepository.findByAccountId(accountId);
    }
}
