package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.configs.service.AccountService;
import com.thi.realestateplatformsprojectbe.configs.service.JwtService;
import com.thi.realestateplatformsprojectbe.dto.DemandDTO;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.models.Role;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import com.thi.realestateplatformsprojectbe.services.impl.BuyerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> getAllDemand(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            String userName = jwtService.getUsernameFromJwtToken(token);
            Account account = accountService.findByEmail(userName);
            if (account != null) {
                for (Role role : account.getRoles()) {
                    if (role.getName().equals("ROLE_ADMIN") || role.getName().equals("ROLE_EMPLOYEE")) {
                        List<Demand> demands = demandService.findAll();
                        if (demands.isEmpty()) {
                            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                        }
                        return new ResponseEntity<>(demands, HttpStatus.OK);
                    }
                }
            }
        }
        List<Demand> demands = demandService.findAllVerifiedDemand();
        if (demands.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(demands, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Demand>> searchRealEstates(
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) List<String> region,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) List<String> realEstateType,
            @RequestParam(required = false) Integer minArea,
            @RequestParam(required = false) Integer maxArea,
            HttpServletRequest request
    ) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            String userName = jwtService.getUsernameFromJwtToken(token);
            Account account = accountService.findByEmail(userName);
            if (account != null) {
                for (Role role : account.getRoles()) {
                    if (role.getName().equals("ROLE_ADMIN") || role.getName().equals("ROLE_EMPLOYEE")) {
                        List<Demand> demands = demandService.searchDemand(notes,region, type,realEstateType, minArea, maxArea);
                        if (demands.isEmpty()) {
                            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                        }
                        return new ResponseEntity<>(demands, HttpStatus.OK);
                    }
                }
            }
        }

        List<Demand> demands = demandService.searchVerifiedDemand(notes,region, type,realEstateType, minArea, maxArea,true);
        if (demands.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(demands, HttpStatus.OK);
        }
    }

    @PutMapping("{id}/verify")
    public ResponseEntity<?> verifyDemand(@PathVariable Long id,HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            String userName = jwtService.getUsernameFromJwtToken(token);
            Account account = accountService.findByEmail(userName);
            if (account != null) {
                for (Role role : account.getRoles()) {
                    if (role.getName().equals("ROLE_ADMIN") || role.getName().equals("ROLE_EMPLOYEE")) {
                        demandService.verifyDemand(id);
                        return new ResponseEntity<>( HttpStatus.OK);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDemand(@PathVariable Long id,HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            String userName = jwtService.getUsernameFromJwtToken(token);
            Account account = accountService.findByEmail(userName);
            if (account != null) {
                for (Role role : account.getRoles()) {
                    if (role.getName().equals("ROLE_ADMIN") || role.getName().equals("ROLE_EMPLOYEE")) {
                        Demand demand = demandService.findById(id);
                        if(demand != null) {
                            demandService.delete(demand);
                            return new ResponseEntity<>(HttpStatus.OK);
                        }
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<DemandDTO> addDemand(@RequestBody DemandDTO demandDTO, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            String userName = jwtService.getUsernameFromJwtToken(token);
            Account account = accountService.findByEmail(userName);
            if (account != null) {
                Buyer buyer = buyerService.findByAccountId(account.getId());
                demandService.save(demandDTO,buyer);
                return new ResponseEntity<>(demandDTO, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

//    @PutMapping("{id}")
//    public ResponseEntity<?> updateDemand(@PathVariable Long id, @RequestBody DemandDTO demandDTO) {
//
//        demandService.save(demandDTO);
//        return new ResponseEntity<>(demandDTO, HttpStatus.OK);
//    }
}