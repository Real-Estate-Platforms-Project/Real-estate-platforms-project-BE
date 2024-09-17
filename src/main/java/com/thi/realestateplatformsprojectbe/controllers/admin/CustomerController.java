package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.configs.UserPrinciple;
import com.thi.realestateplatformsprojectbe.configs.service.AccountService;
import com.thi.realestateplatformsprojectbe.dto.CustomerDTO;
import com.thi.realestateplatformsprojectbe.dto.CustomerUpdateDTO;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Role;
import com.thi.realestateplatformsprojectbe.models.RoleName;
import com.thi.realestateplatformsprojectbe.services.IBuyerService;
import com.thi.realestateplatformsprojectbe.services.ICustomerService;
import com.thi.realestateplatformsprojectbe.services.ISellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final ICustomerService customerService;
    private final AccountService accountService;
    private final IBuyerService buyerService;
    private final ISellerService sellerService;

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

    @PostMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateDTO customer, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Account account = accountService.findByEmail(userPrinciple.getUsername());

        for (Role role : account.getRoles()) {
            if (
                    role.getName().equals(RoleName.ROLE_EMPLOYEE.toString())
                            ||
                            role.getName().equals(RoleName.ROLE_ADMIN.toString())
            ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin tài khoản nhân viên không thể chỉnh sửa");
            }
            if (role.getName().equals(RoleName.ROLE_BUYER.toString())) {
                buyerService.update(account.getId(), customer);
            }
            if (role.getName().equals(RoleName.ROLE_SELLER.toString())) {
                sellerService.update(account.getId(), customer);
            }
        }
        return ResponseEntity.ok("Cập nhật thông tin thành công.");
    }
}