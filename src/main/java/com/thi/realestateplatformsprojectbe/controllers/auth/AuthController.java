package com.thi.realestateplatformsprojectbe.controllers.auth;

import com.thi.realestateplatformsprojectbe.config.service.AccountService;
import com.thi.realestateplatformsprojectbe.config.service.JwtResponse;
import com.thi.realestateplatformsprojectbe.config.service.JwtService;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Role;
import com.thi.realestateplatformsprojectbe.models.RoleName;
import com.thi.realestateplatformsprojectbe.services.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        System.out.println(passwordEncoder.matches("123", "$2a$10$8f.HWlcnK78qqMRiZhSpcOK8FGfHv8a4XpeKfgDKNp19tEvboS5tS"));
        Authentication authentication
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getAccountName(), account.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account currentAccount = accountService.findByAccountName(account.getAccountName());
        return ResponseEntity.ok(new JwtResponse(currentAccount.getId(), jwt, userDetails.getUsername(), userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
//        encoderPassword
        String pw = passwordEncoder.encode(account.getPassword());
        account.setPassword(pw);
//        set Roles mac dinh
        Set<Role> roles = new HashSet<>();
        Role role = roleService.findByName(RoleName.ROLE_BUYER.toString());
        roles.add(role);
        account.setRoles(roles);
//        luu lai vao db
        accountService.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}