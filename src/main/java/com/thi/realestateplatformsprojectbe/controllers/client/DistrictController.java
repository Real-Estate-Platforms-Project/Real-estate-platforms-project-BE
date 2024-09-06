package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.models.District;
import com.thi.realestateplatformsprojectbe.services.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/district")
public class DistrictController {

    @Autowired
    private IDistrictService districtService;

    @GetMapping
    public ResponseEntity<List<District>> getAllDistrictsByProvince(@RequestParam String code) {
        List<District> districts = districtService.findAllByProvinceCode(code);
        return ResponseEntity.ok(districts);
    }
}
