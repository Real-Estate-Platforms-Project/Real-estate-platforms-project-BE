package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.dto.RealEstateWithDetailDTO;
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
public class RealEstateController {

    @Autowired
    private IRealEstateService realEstateService;

    @PostMapping
    public ResponseEntity<RealEstate> addRealEstatePost(@RequestBody RealEstateWithDetailDTO realEstatePostDTO) {
        RealEstate post = realEstateService.addRealEstatePost(realEstatePostDTO);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstate> findRealEstateById(@PathVariable Long id) {
        RealEstate get = realEstateService.findById(id);
        return ResponseEntity.ok(get);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RealEstate>> searchRealEstates(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer minArea,
            @RequestParam(required = false) Integer maxArea,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RealEstate> realEstates = realEstateService.searchRealEstates(address, minPrice, maxPrice, location, type, minArea, maxArea, pageable);

        if (realEstates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(realEstates, HttpStatus.OK);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<RealEstate>> findAllRealEstates() {
        List<RealEstate> realEstates = realEstateService.getAll();
        if (realEstates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(realEstates, HttpStatus.OK);
        }
    }

}
