package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.configs.UserPrinciple;
import com.thi.realestateplatformsprojectbe.configs.service.AccountService;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Seller;
import com.thi.realestateplatformsprojectbe.services.ISellerService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final ISellerService sellerService;
    private final AccountService accountService;

    @GetMapping
    @PermitAll
    public ResponseEntity<?> getAllSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        if (sellers.isEmpty()) {
            return ResponseEntity.status(404).body("Không có người bán nào cả.");
        }
        return ResponseEntity.ok(sellers);
    }

    @PostMapping
    @PermitAll
    public ResponseEntity<Seller> addSeller(@RequestBody Seller seller) {
        return ResponseEntity.ok(sellerService.addSeller(seller));
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<?> getSellerDetails(@PathVariable Long id) {
        Seller seller = sellerService.getSellerById(id);
        if (seller == null) {
            return ResponseEntity.status(404).body("Không tìm thấy người bán với ID: " + id);
        }
        return ResponseEntity.ok(seller);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getSeller(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Account account = accountService.findByEmail(userPrinciple.getUsername());
        // tra loi k phai seller
        //xs
        // check role seller is present?
        if (accountService.checkRole(account)) {
            Seller seller = sellerService.findByAccountId(account.getId());
            return ResponseEntity.ok(seller);
            // neu k co
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Tài khoản này không phải là người bán (seller).");
        }
    }

    @GetMapping("/search")
    @PermitAll
    public ResponseEntity<?> searchSellers(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {
        List<Seller> sellers = sellerService.searchSellers(code, name, email, phoneNumber);
        if (sellers.isEmpty()) {
            return ResponseEntity.status(404).body("Không có người mua nào khớp với tiêu chí tìm kiếm.");
        }
        return ResponseEntity.ok(sellers);
    }


}