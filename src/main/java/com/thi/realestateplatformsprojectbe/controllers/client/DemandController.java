package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
