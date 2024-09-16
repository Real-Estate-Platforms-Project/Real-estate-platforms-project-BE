package com.thi.realestateplatformsprojectbe.configs.service;

import com.thi.realestateplatformsprojectbe.configs.UserPrinciple;
import com.thi.realestateplatformsprojectbe.dto.UpdateAccount;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Role;
import com.thi.realestateplatformsprojectbe.models.RoleName;
import com.thi.realestateplatformsprojectbe.models.VerificationToken;
import com.thi.realestateplatformsprojectbe.repositories.IAccountRepository;
import com.thi.realestateplatformsprojectbe.services.IVerificationTokenService;
import com.thi.realestateplatformsprojectbe.services.email.ConfirmEmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private IAccountRepository accountRepository;


    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public UserDetails loadUserByUsername(String email) {
        return UserPrinciple.build(accountRepository.findByEmail(email));
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public boolean checkRole(Account account) {
        for (Role role : account.getRoles()) {
            if (RoleName.valueOf(role.getName()) == RoleName.ROLE_SELLER) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRoleBuyer(Account account) {
        for (Role role : account.getRoles()) {
            if (RoleName.valueOf(role.getName()) == RoleName.ROLE_BUYER) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<?> updatePassword(Authentication authentication, UpdateAccount updateAccount, PasswordEncoder passwordEncoder) {

        // Lấy thông tin tài khoảng hiện tại
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Account account1 = findByEmail(userPrinciple.getUsername());

        // Xác định tài khoảng có tồn tại không
        if (account1 == null) {
            return new ResponseEntity<>("Tài khoảng không tồn tại", HttpStatus.NOT_FOUND);
        }
        // Xác minh mật khẩu hiện tại nhập vào có đúng không
        boolean isTrue = passwordEncoder.matches(updateAccount.getRecentPassWord(), account1.getPassword());
        if (!isTrue) {
            return new ResponseEntity<>("Mật khẩu hiện tại nhập không đúng", HttpStatus.BAD_REQUEST);
        }
        // xác minh mật khẩu nhập lại có trùng với mật khẩu nhập mới không
        if (!updateAccount.getNewPassWord().equals(updateAccount.getReEnterPassWord())) {
            return new ResponseEntity<>("Nhập lại mật khẩu không đúng", HttpStatus.BAD_REQUEST);
        }
        // Mã hoá encoder mật khẩu mới
        String pw = passwordEncoder.encode(updateAccount.getNewPassWord());

        // Lưu vào db
        account1.setPassword(pw);
        save(account1);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    public ResponseEntity<?> createToken(String email, IVerificationTokenService verificationTokenService, ConfirmEmailService confirmEmailService) throws MessagingException {
        Account account = findByEmail(email);
        VerificationToken token = verificationTokenService.createVerificationToken(account);
        String confirmationUrl = "http://localhost:3000/confirm-email?token=" + token.getToken(); // Frontend URL
        confirmEmailService.sendVerifyEmail(account.getEmail(), confirmationUrl);
        return ResponseEntity.ok(" Vui lòng kiểm tra email để kích hoạt tài khoản.");
    }

    public ResponseEntity<?> confirmEmail(String token, IVerificationTokenService verificationTokenService) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);

        if (verificationToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token không hợp lệ!");
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            verificationTokenService.deleteToken(verificationToken);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token đã hết hạn! Vui lòng yêu cầu gửi lại email xác minh.");
        }


        return ResponseEntity.ok("Chuyển đến trang cập nhật mật khẩu!");
    }

    public ResponseEntity<?> updateForgetPassword(IVerificationTokenService verificationTokenService, String token,
                                                  UpdateAccount updateAccount, PasswordEncoder passwordEncoder) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token không hợp lệ!");
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            verificationTokenService.deleteToken(verificationToken);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token đã hết hạn! Vui lòng yêu cầu gửi lại email xác minh.");
        }
        if (!updateAccount.getNewPassWord().equals(updateAccount.getReEnterPassWord())) {
            return new ResponseEntity<>("Nhập lại mật khẩu không đúng", HttpStatus.BAD_REQUEST);
        }
        Account account = verificationToken.getAccount();
        String pw = passwordEncoder.encode(updateAccount.getNewPassWord());
        account.setPassword(pw);
        save(account);

        verificationTokenService.deleteToken(verificationToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Account> checkAndUpdateExpiredAccounts(){
        return accountRepository.findAllByExpiryDateBefore(LocalDateTime.now());
    }



}

//    public boolean checkExpiryDate(String email) {
//        Account account = findByEmail(email);
//        if (LocalDateTime.now().isAfter(account.getExpiryDate())
//                && LocalDateTime.now().isBefore(account.getExpiryDate().plusDays(5))) {
//            return true;
//        }
//        return false;
//    }

//    new ResponseEntity<>(" Tài khoản còn 5 ngày hoạt động, đổi mật khẩu để tiếp tục sử dụng", HttpStatus.OK);

//    new ResponseEntity<>("Tài khoản hết hạn", HttpStatus.OK);















