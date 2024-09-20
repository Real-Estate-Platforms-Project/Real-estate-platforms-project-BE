package com.thi.realestateplatformsprojectbe.controllers.auth;

import com.thi.realestateplatformsprojectbe.configs.UserPrinciple;
import com.thi.realestateplatformsprojectbe.configs.service.AccountService;
import com.thi.realestateplatformsprojectbe.configs.service.JwtResponse;
import com.thi.realestateplatformsprojectbe.configs.service.JwtService;
import com.thi.realestateplatformsprojectbe.dto.AccountDTO;

import com.thi.realestateplatformsprojectbe.dto.UpdateAccount;
import com.thi.realestateplatformsprojectbe.dto.UserDTO;
import com.thi.realestateplatformsprojectbe.models.*;
import com.thi.realestateplatformsprojectbe.services.IBuyerService;
import com.thi.realestateplatformsprojectbe.services.ISellerService;
import com.thi.realestateplatformsprojectbe.services.IVerificationTokenService;
import com.thi.realestateplatformsprojectbe.services.email.ConfirmEmailService;
import com.thi.realestateplatformsprojectbe.services.email.EmailService;
import com.thi.realestateplatformsprojectbe.services.email.NotifyEmailToChangePasswordService;
import com.thi.realestateplatformsprojectbe.services.impl.BuyerService;
import com.thi.realestateplatformsprojectbe.services.impl.EmployeeService;
import com.thi.realestateplatformsprojectbe.services.impl.SellerService;
import com.thi.realestateplatformsprojectbe.services.role.IRoleService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AccountService accountService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final IVerificationTokenService verificationTokenService;

    private final ISellerService sellerService;

    private final ConfirmEmailService confirmEmailService;

    private final IBuyerService buyerService;
    private final EmployeeService employeeService;
    private final NotifyEmailToChangePasswordService notifyEmailToChangePasswordService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thông tin đăng nhập không chính xác!");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Account currentAccount = accountService.findByEmail(account.getEmail());

        if (!currentAccount.getIsActive()) {
            VerificationToken token = verificationTokenService.getVerificationTokenByAccount(currentAccount);

            if (token == null || token.getExpiryDate().isBefore(LocalDateTime.now().minusMinutes(5))) {
                VerificationToken newToken = verificationTokenService.createVerificationToken(currentAccount);
                String confirmationUrl = "http://localhost:3000/activation-success?token=" + newToken.getToken();
                try {
                    emailService.sendVerifyEmail(currentAccount.getEmail(), confirmationUrl);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Tài khoản chưa được kích hoạt! Email xác nhận đã được gửi lại. Vui lòng kiểm tra email.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Tài khoản chưa được kích hoạt! Vui lòng kiểm tra email để kích hoạt tài khoản.");
            }
        }

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(currentAccount.getId(), jwt, userDetails.getAuthorities()));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountDTO accountDTO) throws MessagingException {
        if (accountService.existsByEmail(accountDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email đã tồn tại!");
        }
        if (!accountDTO.getPassword().equals(accountDTO.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu không khớp!");
        }

        Account account = new Account();
//        account.setUpdateDay(LocalDateTime.now());
//        account.setExpiryDate(account.getUpdateDay().plusDays(45));
        account.setEmail(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setName(accountDTO.getName());

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(RoleName.ROLE_BUYER.toString()));
        account.setRoles(roles);

        accountService.save(account);

        buyerService.createBuyerRegister(account);

        VerificationToken token = verificationTokenService.createVerificationToken(account);
        String confirmationUrl = "http://localhost:3000/activation-success?token=" + token.getToken();
        emailService.sendVerifyEmail(account.getEmail(), confirmationUrl);

        return ResponseEntity.ok("Đăng ký thành công! Vui lòng kiểm tra email để kích hoạt tài khoản.");
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);

        if (verificationToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token không hợp lệ!");
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            verificationTokenService.deleteToken(verificationToken);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token đã hết hạn!");
        }

        Account account = verificationToken.getAccount();
        account.setIsActive(true);
        accountService.save(account);
        verificationTokenService.deleteToken(verificationToken);

        return ResponseEntity.ok("Tài khoản đã được kích hoạt thành công!");
    }


    @PutMapping("/updatePassWord")
    public ResponseEntity<?> editAccount(
            Authentication authentication,
            @RequestBody UpdateAccount updateAccount
    ) {
        return accountService.updatePassword(authentication, updateAccount, passwordEncoder);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        Account account = accountService.findByEmail(userPrinciple.getUsername());
        IUser user = null;
        for (Role role : account.getRoles()) {
            if (role.getName().equals(RoleName.ROLE_BUYER.toString())) {
                user = buyerService.findByAccountId(account.getId());
            }
            if (role.getName().equals(RoleName.ROLE_SELLER.toString())) {
                user = sellerService.findByAccountId(account.getId());
            }
            if (
                    role.getName().equals(RoleName.ROLE_EMPLOYEE.toString())
                    ||
                    role.getName().equals(RoleName.ROLE_ADMIN.toString())
            ) {
                user = employeeService.findByAccountId(account.getId());
            }
        }

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUser(user);
        userDTO.setRoles(account.getRoles());

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @GetMapping("/get-roles")
    public ResponseEntity<?> getAllRole(Authentication authentication) {

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Account account = accountService.findByEmail(userPrinciple.getUsername());

        if (account != null) {
            Set<Role> roles = account.getRoles();
            return ResponseEntity.ok(roles);
            // neu k co
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Tài khoản này không có quyền truy cập");
        }
    }


    @PostMapping("/createToken/{email}")
    public ResponseEntity<?> createToken(@PathVariable String email) throws MessagingException {
        return accountService.createToken(email, verificationTokenService, confirmEmailService);
    }

    @GetMapping("/confirmEmail")
    public ResponseEntity<?> confirmEmail(@RequestParam("token") String token) {
        return accountService.confirmEmail(token, verificationTokenService);
    }

    @PutMapping("/updateForgetPassword")
    public ResponseEntity<?> updateForgetPassword(@RequestParam("token") String token,
                                                  @RequestBody UpdateAccount updateAccount) {
        return accountService.updateForgetPassword(verificationTokenService, token, updateAccount, passwordEncoder);
    }

    @GetMapping("/getExpiryDate")
    public ResponseEntity<?> getExpiryDate(Authentication authentication) {
        // Lấy thông tin tài khoảng hiện tại
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Account account = accountService.findByEmail(userPrinciple.getUsername());
        return new ResponseEntity<>(account.getExpiryDate(), HttpStatus.OK);
    }

    @GetMapping("/checkDateToChangePassword")
    public ResponseEntity<?> checkDateToChangePassword(Authentication authentication) {
        // Lấy thông tin tài khoảng hiện tại
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Account account = accountService.findByEmail(userPrinciple.getUsername());

        boolean isTrue = LocalDateTime.now().isAfter(account.getUpdateDay().plusDays(30));
        if (isTrue) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }


    @GetMapping("/isDeleted/{email}")
    public ResponseEntity<?> updateForgetPassword(@PathVariable String email) {
        Account account = accountService.findByEmail(email);
        account.setIsDeleted(false);
        accountService.save(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/checkIsDeleted")
    public ResponseEntity<?> checkIsDeleted(Authentication authentication) {
        // Lấy thông tin tài khoảng hiện tại
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Account account = accountService.findByEmail(userPrinciple.getUsername());

        boolean isDeleted = account.getIsDeleted();
        if (isDeleted) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);

    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void checkAndUpdateExpiredAccounts() {
        List<Account> expiredAccounts = accountService.findAllByExpiryDateBefore();
        expiredAccounts.forEach(account -> {
            account.setIsDeleted(true);
            accountService.save(account);
        });
    }


    //    @Scheduled(fixedDelay = 60000)
    @Scheduled(cron = "0 0 0 * * ?")
    public void NotifyEmailToChangePassword() throws MessagingException {
        List<Account> expiredAccounts = accountService.accountsWhichOver30DayPassHaveNotChangePassword();
        LocalDateTime now = LocalDateTime.now();
        long daysBetween;
        for (Account item : expiredAccounts) {
            daysBetween = ChronoUnit.DAYS.between(now, item.getExpiryDate());
            if (daysBetween <= 15) {
                notifyEmailToChangePasswordService.sendNotiFyEmailChangePassword(item.getEmail(), daysBetween);
            }
        }
    }


}



