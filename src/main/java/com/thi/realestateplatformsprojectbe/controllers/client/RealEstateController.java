package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.dto.RealEstateDTO;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.services.IRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/real-estate")
public class RealEstateController {

    @Autowired
    private IRealEstateService realEstateService;

    @PostMapping
    public ResponseEntity<RealEstate> addRealEstatePost(@RequestBody RealEstateDTO realEstatePostDTO) {
        RealEstate post = realEstateService.addRealEstatePost(realEstatePostDTO);
        return ResponseEntity.ok(post);
    }

}
