package com.thi.realestateplatformsprojectbe.controllers.auth;

import com.thi.realestateplatformsprojectbe.configs.UserPrinciple;
import com.thi.realestateplatformsprojectbe.configs.service.AccountService;
import com.thi.realestateplatformsprojectbe.configs.service.JwtResponse;
import com.thi.realestateplatformsprojectbe.configs.service.JwtService;

import com.thi.realestateplatformsprojectbe.dto.UpdateAccount;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Role;
import com.thi.realestateplatformsprojectbe.models.RoleName;
import com.thi.realestateplatformsprojectbe.services.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PutMapping("/updatePassWord/{accountName}")
//    public ResponseEntity<?> editAccount(
//            @RequestBody Account account,
//            Authentication authentication
//            ) {
//        authentication.getPrincipal();
//        Account account1 = accountService.findByAccountName(accountName);
//        if (account1 == null) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        account1.setId(account.getId());
//        account1.setIsDeleted();
//        account1.setPassword(account.getPassword());
//
//        String pw = passwordEncoder.encode(account1.getPassword());
//        account1.setPassword(pw);
//        Set<Role> roles = new HashSet<>();
//
//        Role role = roleService.findByName(RoleName.ROLE_BUYER.toString());
//        roles.add(role);
//        account1.setRoles(roles);
//
//        accountService.save(account1);
//        return new ResponseEntity<>(account,HttpStatus.OK);
//    }

    @PutMapping("/updatePassWord")
    public ResponseEntity<?> editAccount(
            Authentication authentication,
            @RequestBody UpdateAccount updateAccount
    ) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Account account1 = accountService.findByAccountName(userPrinciple.getUsername());
        if (account1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!updateAccount.getNewPassWord().equals(updateAccount.getReEnterPassWord())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String pw = passwordEncoder.encode(updateAccount.getNewPassWord());
        account1.setPassword(pw);
        Set<Role> roles = new HashSet<>();
        Role role = roleService.findByName(RoleName.ROLE_BUYER.toString());
        roles.add(role);
        account1.setRoles(roles);
        accountService.save(account1);
        return new ResponseEntity<>(userPrinciple.getPassword(), HttpStatus.OK);
    }
}