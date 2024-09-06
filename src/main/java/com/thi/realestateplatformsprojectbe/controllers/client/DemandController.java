package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demand")
public class DemandController {
    @Autowired
    private IDemandService demandService;

    @GetMapping
    public ResponseEntity<?> getAllDemand() {
        List<Demand> demands = demandService.findAll();
        return new ResponseEntity<>(demands, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> deleteStudent(@RequestBody Demand demand) {
        demandService.delete(demand);
        return new ResponseEntity<>(demand, HttpStatus.OK);
    }
}
