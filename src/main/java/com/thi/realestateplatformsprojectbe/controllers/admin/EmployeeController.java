package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.dto.EmployeeDTO;
import com.thi.realestateplatformsprojectbe.models.Employee;
import com.thi.realestateplatformsprojectbe.services.IEmployeeService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PermitAll
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        Iterable<Employee> employees = employeeService.getAllEmployees();
        if (employees == null) {
            return ResponseEntity.status(404).body("Không có nhân viên nào cả.");
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee createdEmployee = employeeService.createEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        boolean isDeleted = employeeService.deleteEmployee(id);
        if (isDeleted) {
            return ResponseEntity.ok("Nhân viên đã được xóa thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nhân viên không tồn tại.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        Optional<Employee> updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        if (updatedEmployee.isPresent()) {
            return ResponseEntity.ok(updatedEmployee.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nhân viên không tồn tại.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEmployees(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "position", required = false) String position) {
        Iterable<Employee> employees = employeeService.searchEmployees(code, name, email, position);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = employeeService.emailExists(email);
        return ResponseEntity.ok(exists);
    }
}


