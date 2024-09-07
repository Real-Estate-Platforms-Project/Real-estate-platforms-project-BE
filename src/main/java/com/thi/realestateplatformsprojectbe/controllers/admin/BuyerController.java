package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.services.IBuyerService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/buyers")
public class BuyerController {

    @Autowired
    private IBuyerService buyerService;

    @GetMapping
    @PermitAll
    public ResponseEntity<?> getAllBuyers() {
        List<Buyer> buyers = buyerService.getAllBuyers();
        if (buyers.isEmpty()) {
            return ResponseEntity.status(404).body("Không có khách hàng nào cả.");
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
        Buyer buyer = buyerService.getBuyerById(id);
        if (buyer == null) {
            return ResponseEntity.status(404).body("Không tìm thấy khách hàng với ID: " + id);
        }
        return ResponseEntity.ok(buyer);
    }
}