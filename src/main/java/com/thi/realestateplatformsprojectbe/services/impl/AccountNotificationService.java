package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.AccountNotificationDTO;
import com.thi.realestateplatformsprojectbe.dto.RealEstateWithDetailDTO;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.AccountNotification;
import com.thi.realestateplatformsprojectbe.models.Employee;
import com.thi.realestateplatformsprojectbe.models.Seller;
import com.thi.realestateplatformsprojectbe.repositories.IAccountNotificationRepository;
import com.thi.realestateplatformsprojectbe.repositories.IAccountRepository;
import com.thi.realestateplatformsprojectbe.repositories.IEmployeeRepository;
import com.thi.realestateplatformsprojectbe.repositories.ISellerRepository;
import com.thi.realestateplatformsprojectbe.services.IAccountNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountNotificationService implements IAccountNotificationService {

    @Autowired
    private IAccountNotificationRepository accountNotificationRepository;

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private ISellerRepository sellerRepository;


    @Override
    public void addNotification(RealEstateWithDetailDTO realEstatePostDTO) {
        List<Employee> employees = employeeRepository.findAll();
        Seller seller = sellerRepository.findById(realEstatePostDTO.getSellerId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản người dùng"));
        Account account = seller.getAccount();
        for (Employee employee : employees) {
            Account employeeAccount = employee.getAccount();
            AccountNotification employeeNotification = AccountNotification.builder()
                    .name("Người dùng " + account.getName() + " vừa đăng một tin bất động sản mới")
                    .reading(false)
                    .account(employeeAccount)
                    .build();
            accountNotificationRepository.save(employeeNotification);
        }
    }

    @Override
    public List<AccountNotification> findAllByAccountId(Long accountId) {
        return accountNotificationRepository.findAllByAccountId(accountId);
    }

}
