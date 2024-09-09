package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAnyRole;


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

    @GetMapping("/verify/id")
    public boolean verifyDemand(@RequestParam Long id) {
        return demandService.verifyDemand(id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDemand(@RequestBody Demand demand) {
        demandService.delete(demand);
        return new ResponseEntity<>(demand, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Demand> addDemand(@RequestBody Demand demand) {
        demandService.save(demand);
        return new ResponseEntity<>(demand, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateDemand(@RequestBody Demand demand) {
        demandService.save(demand);
        return new ResponseEntity<>(demand, HttpStatus.OK);
    }
}
