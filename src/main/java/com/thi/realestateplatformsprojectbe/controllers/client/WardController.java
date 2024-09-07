package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.models.Ward;
import com.thi.realestateplatformsprojectbe.services.IWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/ward")
public class WardController {

    @Autowired
    private IWardService wardService;

    @GetMapping
    public ResponseEntity<List<Ward>> getAllWards(@RequestParam String code) {
        List<Ward> wards = wardService.findAllByDistrictCode(code);
        return ResponseEntity.ok(wards);
    }
}
