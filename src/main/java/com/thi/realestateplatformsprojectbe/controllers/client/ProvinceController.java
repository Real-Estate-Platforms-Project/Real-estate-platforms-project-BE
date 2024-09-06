package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.models.Province;
import com.thi.realestateplatformsprojectbe.services.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/province")
public class ProvinceController {

    @Autowired
    private IProvinceService provinceService;

    @GetMapping
    public ResponseEntity<List<Province>> getAllProvinces() {
        List<Province> provinces = provinceService.findAll();
        return ResponseEntity.ok(provinces);
    }
}
