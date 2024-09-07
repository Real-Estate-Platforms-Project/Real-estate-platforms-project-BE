package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.services.IBuyerService;
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
    public ResponseEntity<List<Buyer>> getAllBuyers() {
        return ResponseEntity.ok(buyerService.getAllBuyers());
    }

    @PostMapping
    public ResponseEntity<Buyer> addBuyer(@RequestBody Buyer buyer) {
        return ResponseEntity.ok(buyerService.addBuyer(buyer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buyer> getBuyerDetails(@PathVariable Long id) {
        return ResponseEntity.ok(buyerService.getBuyerById(id));
    }
}