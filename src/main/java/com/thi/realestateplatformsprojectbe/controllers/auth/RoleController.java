package com.thi.realestateplatformsprojectbe.controllers.auth;

import com.thi.realestateplatformsprojectbe.models.DTO.GetNumberOfRole;
import com.thi.realestateplatformsprojectbe.services.role.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/role")
public class RoleController {
    private final IRoleService roleService;

    @GetMapping
    public ResponseEntity<Iterable<GetNumberOfRole>> getRole(){
        Iterable<GetNumberOfRole> getNumberOfRoles = roleService.getAllNumberOfRole();
        return new ResponseEntity<>(getNumberOfRoles, HttpStatus.OK);
    }
}
