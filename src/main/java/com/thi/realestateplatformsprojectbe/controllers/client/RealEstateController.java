package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.dto.RealEstateDTO;
import com.thi.realestateplatformsprojectbe.services.IRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/real-estate-posts")
public class RealEstateController {

    @Autowired
    private IRealEstateService realEstateService;

    @PostMapping
    public ResponseEntity<RealEstate> addRealEstatePost(@RequestBody RealEstateDTO realEstatePostDTO) {
        RealEstate post = realEstateService.addRealEstatePost(realEstatePostDTO);
        return ResponseEntity.ok(post);
    }

}
