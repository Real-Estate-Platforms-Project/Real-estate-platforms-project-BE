package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.Employee;

public interface IEmployeeService {
    Employee findByAccountId(Long accountId);
}
