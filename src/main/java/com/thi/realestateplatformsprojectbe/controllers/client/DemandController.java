package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/demand")
public class DemandController {
    @Autowired
    private IDemandService demandService;

//    @PreAuthorize("hasAnyRole()")
    @GetMapping
    public ResponseEntity<?> getAllDemand() {
        List<Demand> demands = demandService.findAll();
        if (demands.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(demands, HttpStatus.OK);
    }
// tai danh sach cac demand chua duoc verify cho admin duyet
    @GetMapping("/validate")
    public ResponseEntity<?> getInvalidatedDemand() {
        List<Demand> demands = demandService.findInvalidatedDemand();
        return new ResponseEntity<>(demands, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}/verify")
    public boolean verifyDemand(@PathVariable Long id) {
        return demandService.verifyDemand(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDemand(@PathVariable Long id) {
        Demand demand = demandService.findById(id);
        if (demand != null) {
            demandService.delete(demand);
            return new ResponseEntity<>(demand, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Demand> addDemand(@RequestBody Demand demand) {
        demandService.save(demand);
        return new ResponseEntity<>(demand, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateDemand(@PathVariable Long id, @RequestBody Demand demand) {

        demandService.save(demand);
        return new ResponseEntity<>(demand, HttpStatus.OK);
    }
}
