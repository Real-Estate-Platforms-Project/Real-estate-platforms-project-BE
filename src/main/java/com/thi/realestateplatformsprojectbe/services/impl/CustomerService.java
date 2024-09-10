package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.CustomerDTO;
import com.thi.realestateplatformsprojectbe.models.*;
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

    public void addNewCustomer(CustomerDTO customerDTO) throws MessagingException {
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
            seller.setAddressLine(customerDTO.getAddress());
            seller.setEmail(customerDTO.getEmail());
            seller.setPhoneNumber(customerDTO.getPhoneNumber());
            seller.setGender(customerDTO.getGender());
            seller.setIdNumber(customerDTO.getIdCard());
            seller.setCode("SELLER" + generateRandomCode());
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
            buyer.setCode("BUYER" + generateRandomCode());
            buyerRepository.save(buyer);
        }

        emailService.sendAccountCreationEmail(customerDTO.getEmail(), customerDTO.getName(), tempPassword);
    }
    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    private String generateRandomCode() {
        return RandomStringUtils.randomNumeric(4);
    }
}