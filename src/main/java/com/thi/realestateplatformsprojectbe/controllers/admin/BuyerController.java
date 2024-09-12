package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.configs.UserPrinciple;
import com.thi.realestateplatformsprojectbe.configs.service.AccountService;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.services.IBuyerService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/buyers")
public class BuyerController {

    @Autowired
    private IBuyerService buyerService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    @PermitAll
    public ResponseEntity<?> getAllBuyers() {
        List<Buyer> buyers = buyerService.getAllBuyers();
        if (buyers.isEmpty()) {
            return ResponseEntity.status(404).body("Không có người mua nào cả.");
        }
        return ResponseEntity.ok(buyers);
    }

    @PostMapping
    @PermitAll
    public ResponseEntity<Buyer> addBuyer(@RequestBody Buyer buyer) {
        return ResponseEntity.ok(buyerService.addBuyer(buyer));
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<?> getBuyerDetails(@PathVariable Long id) {
        Buyer buyer = buyerService.findByAccountId(id);
        if (buyer == null) {
            return ResponseEntity.status(404).body("Không tìm thấy người mua với ID: " + id);
        }
        return ResponseEntity.ok(buyer);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getBuyer(Authentication authentication) {
        if(authentication != null) {
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            Account account = accountService.findByEmail(userPrinciple.getUsername());
            if (accountService.checkRoleBuyer(account)) {
                Buyer buyer = buyerService.findByAccountId(account.getId());
                return ResponseEntity.ok(buyer);
                // neu k co
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Tài khoản này không phải là người mua (buyer).");
            }
        }
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}