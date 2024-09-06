package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.models.Seller;
import com.thi.realestateplatformsprojectbe.services.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sellers")
public class SellerController {

    @Autowired
    private ISellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers() {
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    @PostMapping
    public ResponseEntity<Seller> addSeller(@RequestBody Seller seller) {
        return ResponseEntity.ok(sellerService.addSeller(seller));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerDetails(@PathVariable Long id) {
        return ResponseEntity.ok(sellerService.getSellerById(id));
    }
}