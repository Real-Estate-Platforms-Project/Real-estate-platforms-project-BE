package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.CustomerDTO;
import com.thi.realestateplatformsprojectbe.models.*;
import com.thi.realestateplatformsprojectbe.repositories.*;
import com.thi.realestateplatformsprojectbe.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IBuyerRepository buyerRepository;

    @Autowired
    private ISellerRepository sellerRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Transactional
    public void addNewCustomer(CustomerDTO customerDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(customerDTO.getPassword());

        Account account = new Account();
        account.setEmail(customerDTO.getEmail());
        account.setPassword(encodedPassword);
        account.setIsActive(true);
        account.setIsDeleted(false);
        account = accountRepository.save(account);

        if ("BUYER".equalsIgnoreCase(customerDTO.getCustomerType())) {
            Buyer buyer = new Buyer();
            buyer.setCode("BUYER_CODE");
            buyer.setName(customerDTO.getName());
            buyer.setDob(customerDTO.getDob());
            buyer.setGender(customerDTO.getGender());
            buyer.setAddress(customerDTO.getAddress());
            buyer.setEmail(customerDTO.getEmail());
            buyer.setPhoneNumber(customerDTO.getPhoneNumber());
            buyer.setIdCard(customerDTO.getIdCard());
            buyer.setEnable(true);
            buyer.setIsDeleted(false);
            buyer.setAccount(account);
            buyerRepository.save(buyer);
            linkRoleToAccount(account, RoleName.ROLE_BUYER);
        } else if ("SELLER".equalsIgnoreCase(customerDTO.getCustomerType())) {
            Seller seller = new Seller();
            seller.setCode("SELLER_CODE");
            seller.setName(customerDTO.getName());
            seller.setDob(customerDTO.getDob());
            seller.setGender(customerDTO.getGender());
            seller.setAddressLine(customerDTO.getAddress());
            seller.setEmail(customerDTO.getEmail());
            seller.setPhoneNumber(customerDTO.getPhoneNumber());
            seller.setIdNumber(customerDTO.getIdCard());
            seller.setEnable(true);
            seller.setIsDeleted(false);
            seller.setAccount(account);
            sellerRepository.save(seller);

            linkRoleToAccount(account, RoleName.ROLE_SELLER);
        } else {
            throw new IllegalArgumentException("Invalid customer type");
        }
    }

    private void linkRoleToAccount(Account account, RoleName roleName) {
        String roleNameString = roleName.name();
        Role role = roleRepository.findByName(roleNameString);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        account.getRoles().add(role);
        accountRepository.save(account);
    }
}