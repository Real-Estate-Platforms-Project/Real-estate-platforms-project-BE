package com.thi.realestateplatformsprojectbe.controllers.auth;

import com.thi.realestateplatformsprojectbe.configs.service.AccountService;
import com.thi.realestateplatformsprojectbe.configs.service.JwtResponse;
import com.thi.realestateplatformsprojectbe.configs.service.JwtService;
import com.thi.realestateplatformsprojectbe.dto.AccountDTO;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Role;
import com.thi.realestateplatformsprojectbe.models.RoleName;
import com.thi.realestateplatformsprojectbe.models.VerificationToken;
import com.thi.realestateplatformsprojectbe.services.IVerificationTokenService;
import com.thi.realestateplatformsprojectbe.services.email.EmailService;
import com.thi.realestateplatformsprojectbe.services.role.IRoleService;
import jakarta.mail.MessagingException;
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

import java.time.LocalDateTime;
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
    @Autowired
    private EmailService emailService;
    @Autowired
    private IVerificationTokenService verificationTokenService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Authentication authentication
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account currentAccount = accountService.findByEmail(account.getEmail());
        return ResponseEntity.ok(new JwtResponse(currentAccount.getId(), jwt, userDetails.getUsername(), userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountDTO accountDTO) throws MessagingException {
        if (accountService.existsByEmail(accountDTO.getEmail())) {
            return new ResponseEntity<>("Email đã tồn tại!", HttpStatus.BAD_REQUEST);
        }
        if (!accountDTO.getPassword().equals(accountDTO.getConfirmPassword())) {
            return new ResponseEntity<>("Mật khẩu không khớp!", HttpStatus.BAD_REQUEST);
        }
        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        String pw = passwordEncoder.encode(accountDTO.getPassword());
        account.setPassword(pw);
        Set<Role> roles = new HashSet<>();
        Role role = roleService.findByName(RoleName.ROLE_BUYER.toString());
        roles.add(role);
        account.setRoles(roles);
        accountService.save(account);

        VerificationToken token = verificationTokenService.createVerificationToken(account);

        String confirmationUrl = "http://localhost:8080/api/auth/confirm?token=" + token.getToken();
        emailService.sendVerifyEmail(account.getEmail(), confirmationUrl);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);

        if (verificationToken == null) {
            return new ResponseEntity<>("Token không hợp lệ!", HttpStatus.BAD_REQUEST);
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>("Token đã hết hạn!", HttpStatus.BAD_REQUEST);
        }

        Account account = verificationToken.getAccount();
        account.setIsActive(true);
        accountService.save(account);

        verificationTokenService.deleteToken(verificationToken);

        return new ResponseEntity<>("Tài khoản đã được kích hoạt thành công!", HttpStatus.OK);
    }
}