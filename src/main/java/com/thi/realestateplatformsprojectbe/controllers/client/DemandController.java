package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.configs.service.AccountService;
import com.thi.realestateplatformsprojectbe.configs.service.JwtService;
import com.thi.realestateplatformsprojectbe.dto.DemandDTO;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import com.thi.realestateplatformsprojectbe.services.impl.BuyerService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/demand")
public class DemandController {
    @Autowired
    private IDemandService demandService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private AccountService accountService;


    @GetMapping
    public ResponseEntity<?> getAllVerifiedDemand(@RequestParam(value = "isVerify", defaultValue = "") Boolean isVerify) {
        if (isVerify != null) {
            List<Demand> demands = demandService.findAllVerifiedDemand(isVerify);
            if (demands.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(demands, HttpStatus.OK);
        }
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

    @GetMapping("/search")
    public ResponseEntity<List<Demand>> searchRealEstates(
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String realEstateType,
            @RequestParam(required = false) Integer minArea,
            @RequestParam(required = false) Integer maxArea) {
        List<Demand> demands = demandService.searchDemand(notes,region, type,realEstateType, minArea, maxArea);


        if (demands.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(demands, HttpStatus.OK);
        }
    }

    @PutMapping("{id}/verify")
    public boolean verifyDemand(@PathVariable Long id) {
        return demandService.verifyDemand(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDemand(@PathVariable Long id) {
        Demand demand = demandService.findById(id);
        if (demand != null) {
            demandService.delete(demand);
            return new ResponseEntity<>(demand, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<DemandDTO> addDemand(@RequestBody DemandDTO demandDTO) {
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.replace("Bearer ", "");
//
//            String userName = jwtService.getUsernameFromJwtToken(token);
//            Account account = accountService.getAccountByEmail(userName);
//            Buyer buyer = buyerService.getBuyerByAccountId(account.getId());

            demandService.save(demandDTO);
            return new ResponseEntity<>(demandDTO, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);

    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateDemand(@PathVariable Long id, @RequestBody DemandDTO demandDTO) {

        demandService.save(demandDTO);
        return new ResponseEntity<>(demandDTO, HttpStatus.OK);
    }
}
