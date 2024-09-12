package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.dto.RealEstateDTO;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.services.IRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/real-estate")
public class  RealEstateController {

    @Autowired
    private IRealEstateService realEstateService;

    @PostMapping
    public ResponseEntity<RealEstate> addRealEstatePost(@RequestBody RealEstateDTO realEstatePostDTO) {
        RealEstate post = realEstateService.addRealEstatePost(realEstatePostDTO);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RealEstate>> searchRealEstates(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer minArea,
            @RequestParam(required = false) Integer maxArea,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RealEstate> realEstates = realEstateService.searchRealEstates(minPrice, maxPrice, location, type, minArea, maxArea, pageable);


        if (realEstates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(realEstates, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<RealEstate>> findAll() {
        List<RealEstate> realEstates = realEstateService.findAll();
        if (realEstates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(realEstates, HttpStatus.OK);
        }
    }

}
