package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.CustomerDTO;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Role;
import com.thi.realestateplatformsprojectbe.models.Seller;
import com.thi.realestateplatformsprojectbe.repositories.IAccountRepository;
import com.thi.realestateplatformsprojectbe.repositories.IBuyerRepository;
import com.thi.realestateplatformsprojectbe.repositories.IRoleRepository;
import com.thi.realestateplatformsprojectbe.repositories.ISellerRepository;
import com.thi.realestateplatformsprojectbe.services.ICustomerService;
import com.thi.realestateplatformsprojectbe.services.email.AdminAccountCreationEmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private AdminAccountCreationEmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ISellerRepository sellerRepository;

    @Autowired
    private IBuyerRepository buyerRepository;

    public boolean emailExists(String email) {
        return accountRepository.existsByEmail(email);
    }

    public void addNewCustomer(CustomerDTO customerDTO) throws MessagingException {
        if (emailExists(customerDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại. Vui lòng sử dụng email khác.");
        }

        Account account = new Account();
        account.setEmail(customerDTO.getEmail());
        String tempPassword = generateRandomPassword();
        account.setPassword(passwordEncoder.encode(tempPassword));
        account.setName(customerDTO.getName());
        account.setIsActive(true);

        Role role = roleRepository.findByName("seller".equalsIgnoreCase(customerDTO.getCustomerType()) ? "ROLE_SELLER" : "ROLE_BUYER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        account.setRoles(roles);

        accountRepository.save(account);

        if ("seller".equalsIgnoreCase(customerDTO.getCustomerType())) {
            Seller seller = new Seller();
            seller.setAccount(account);
            seller.setName(customerDTO.getName());
            seller.setDob(customerDTO.getDob());
            seller.setAddress(customerDTO.getAddress());
            seller.setEmail(customerDTO.getEmail());
            seller.setPhoneNumber(customerDTO.getPhoneNumber());
            seller.setGender(customerDTO.getGender());
            seller.setIdCard(customerDTO.getIdCard());
            seller.setCode("REP-" + generateRandomCode());
            seller.setImageUrl(customerDTO.getImageUrl());
            sellerRepository.save(seller);
        } else {
            Buyer buyer = new Buyer();
            buyer.setAccount(account);
            buyer.setName(customerDTO.getName());
            buyer.setDob(customerDTO.getDob());
            buyer.setAddress(customerDTO.getAddress());
            buyer.setEmail(customerDTO.getEmail());
            buyer.setPhoneNumber(customerDTO.getPhoneNumber());
            buyer.setGender(customerDTO.getGender());
            buyer.setIdCard(customerDTO.getIdCard());
            buyer.setCode("REP-" + generateRandomCode());
            buyer.setImageUrl(customerDTO.getImageUrl());
            buyerRepository.save(buyer);
        }

        emailService.sendAccountCreationEmail(customerDTO.getEmail(), customerDTO.getEmail(), tempPassword);
    }

    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    private String generateRandomCode() {
        return RandomStringUtils.randomNumeric(4);
    }

    @Override
    public void updateCustomerRole(Long accountId, String newRole) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản với ID: " + accountId));

        Role role = roleRepository.findByName("ROLE_" + newRole.toUpperCase());
        if (role == null) {
            throw new IllegalArgumentException("Vai trò không hợp lệ.");
        }

        Set<Role> currentRoles = account.getRoles();
        boolean hasRole = currentRoles.stream().anyMatch(r -> r.getName().equals(role.getName()));

        if (hasRole) {
            throw new IllegalArgumentException("Tài khoản đã ở vai trò này rồi.");
        }

        if (newRole.equalsIgnoreCase("seller")) {
            addSellerRoleToAccount(account);
        }

        accountRepository.save(account);
    }

    private void addSellerRoleToAccount(Account account) {
        Role sellerRole = roleRepository.findByName("ROLE_SELLER");
        if (sellerRole != null) {
            account.getRoles().add(sellerRole);
        }

        Buyer buyer = buyerRepository.findByAccount(account);
        if (buyer != null) {
            Seller seller = new Seller();
            seller.setAccount(account);
            seller.setName(buyer.getName());
            seller.setDob(buyer.getDob());
            seller.setAddress(buyer.getAddress());
            seller.setEmail(buyer.getEmail());
            seller.setPhoneNumber(buyer.getPhoneNumber());
            seller.setGender(buyer.getGender());
            seller.setIdCard(buyer.getIdCard());
            seller.setCode(buyer.getCode());
            seller.setImageUrl(buyer.getImageUrl());
            sellerRepository.save(seller);
        }
    }
}
