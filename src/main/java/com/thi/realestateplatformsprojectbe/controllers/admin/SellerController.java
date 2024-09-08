package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.models.Seller;
import com.thi.realestateplatformsprojectbe.services.ISellerService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/sellers")
public class SellerController {

    @Autowired
    private ISellerService sellerService;

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
}