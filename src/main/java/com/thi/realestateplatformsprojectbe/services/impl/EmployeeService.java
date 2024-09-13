package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.EmployeeDTO;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Employee;
import com.thi.realestateplatformsprojectbe.models.Position;
import com.thi.realestateplatformsprojectbe.repositories.IAccountRepository;
import com.thi.realestateplatformsprojectbe.repositories.IEmployeeRepository;
import com.thi.realestateplatformsprojectbe.repositories.IPositionRepository;
import com.thi.realestateplatformsprojectbe.services.IEmployeeService;
import com.thi.realestateplatformsprojectbe.services.email.EmployeeAccountCreationEmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final IAccountRepository accountRepository;
    private final IPositionRepository positionRepository;
    private final EmployeeAccountCreationEmailService emailService;

    @Override
    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findByAccountId(Long accountId) {
        return employeeRepository.findByAccountId(accountId);
    }
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee createEmployee(EmployeeDTO employee) {
        // tự tạo password
        String password = generateRandomPassword();
        String passwordEn = passwordEncoder.encode(password);

        // tim possion
        Position position = positionRepository.findById(employee.getPositionId())
                .orElseThrow(() -> new RuntimeException("Position not found"));

        // cretae account
        Account newAccount = Account.builder()
                .name(employee.getName())
                .email(employee.getEmail())
                .isActive(true)
                .isDeleted(false)
                .password(passwordEn).build();
        newAccount = accountRepository.save(newAccount);

        // create employeee
        Employee newEmployee = Employee.builder()
                .code(employee.getCode())
                .name(employee.getName())
                .dob(employee.getDob())
                .gender(employee.getGender())
                .phoneNumber(employee.getPhoneNumber())
                .email(employee.getEmail())
                .address(employee.getAddress())
                .account(newAccount)
                .position(position)
                .isDeleted(false)
                .isAdmin(employee.getRole().equals("Admin"))
                .build();

        newEmployee = employeeRepository.save(newEmployee);

        try {
            emailService.sendAccountCreationEmail(employee.getEmail(), password);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return newEmployee;
    }

    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Employee> updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployeeOpt = employeeRepository.findById(id);
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();

            existingEmployee.setCode(employeeDTO.getCode());
            existingEmployee.setName(employeeDTO.getName());
            existingEmployee.setDob(employeeDTO.getDob());
            existingEmployee.setGender(employeeDTO.getGender());
            existingEmployee.setPhoneNumber(employeeDTO.getPhoneNumber());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setAddress(employeeDTO.getAddress());


            Position position = positionRepository.findById(employeeDTO.getPositionId())
                    .orElseThrow(() -> new RuntimeException("Position not found"));
            existingEmployee.setPosition(position);


            existingEmployee.setIsAdmin(employeeDTO.getRole().equals("Admin"));

            employeeRepository.save(existingEmployee);
            return Optional.of(existingEmployee);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Employee> searchEmployees(String code, String name, String email, Long positionId) {
        return employeeRepository.searchEmployees(code, name, email, positionId);
    }
}

