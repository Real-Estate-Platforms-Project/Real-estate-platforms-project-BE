package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.dto.CustomerDTO;
import com.thi.realestateplatformsprojectbe.services.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = customerService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            customerService.addNewCustomer(customerDTO);
            return ResponseEntity.ok("Khách hàng đã được thêm thành công!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi thêm khách hàng.");
        }
    }
}