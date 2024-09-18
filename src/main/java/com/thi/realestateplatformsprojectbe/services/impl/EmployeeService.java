package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.EmployeeDTO;
import com.thi.realestateplatformsprojectbe.models.*;
import com.thi.realestateplatformsprojectbe.repositories.IAccountRepository;
import com.thi.realestateplatformsprojectbe.repositories.IEmployeeRepository;
import com.thi.realestateplatformsprojectbe.repositories.IPositionRepository;
import com.thi.realestateplatformsprojectbe.services.IEmployeeService;
import com.thi.realestateplatformsprojectbe.services.email.EmployeeAccountCreationEmailService;
import com.thi.realestateplatformsprojectbe.services.role.IRoleService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final IRoleService roleService;
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
    public boolean emailExists(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        String employeeCode = generateNextEmployeeCode();

        String password = generateRandomPassword();
        String passwordEn = passwordEncoder.encode(password);

        Position position = positionRepository.findById(employeeDTO.getPositionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chức vụ"));
        if (emailExists(employeeDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại. Vui lòng sử dụng email khác.");
        }

        Account newAccount = Account.builder()
                .name(employeeDTO.getName())
                .email(employeeDTO.getEmail())
                .isActive(true)
                .isDeleted(false)
                .password(passwordEn).build();
        String role = RoleName.ROLE_EMPLOYEE.toString();
        Set<Role> roles = new HashSet<>();
        if (employeeDTO.getRole().equals("Admin")) {
            role = RoleName.ROLE_ADMIN.toString();
        }
        roles.add(roleService.findByName(role));
        newAccount.setRoles(roles);
        newAccount = accountRepository.save(newAccount);

        Employee newEmployee = Employee.builder()
                .code(employeeCode)
                .name(employeeDTO.getName())
                .dob(employeeDTO.getDob())
                .gender(employeeDTO.getGender())
                .phoneNumber(employeeDTO.getPhoneNumber())
                .email(employeeDTO.getEmail())
                .address(employeeDTO.getAddress())
                .account(newAccount)
                .position(position)
                .isDeleted(false)
                .isAdmin(employeeDTO.getRole().equals("Admin"))
                .build();

        newEmployee = employeeRepository.save(newEmployee);

        try {
            emailService.sendAccountCreationEmail(employeeDTO.getEmail(), password);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return newEmployee;
    }

    private String generateNextEmployeeCode() {
        String maxCode = employeeRepository.findMaxEmployeeCodeNumber();

        if (maxCode == null) {
            return "MNV-" + maxCode;
        }

        int currentNumber = Integer.parseInt(maxCode);

        int nextNumber = currentNumber + 1;

        return String.format("MNV-%03d", nextNumber);
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


            existingEmployee.setName(employeeDTO.getName());
            existingEmployee.setDob(employeeDTO.getDob());
            existingEmployee.setGender(employeeDTO.getGender());
            existingEmployee.setPhoneNumber(employeeDTO.getPhoneNumber());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setAddress(employeeDTO.getAddress());

            Position position = positionRepository.findById(employeeDTO.getPositionId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chức vụ"));
            existingEmployee.setPosition(position);

            existingEmployee.setIsAdmin(employeeDTO.getRole().equals("Admin"));

            employeeRepository.save(existingEmployee);
            return Optional.of(existingEmployee);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Employee> searchEmployees(String code, String name, String email, String position) {
        return employeeRepository.searchEmployees(code, name, email, position);
    }
}



