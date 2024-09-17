package com.thi.realestateplatformsprojectbe.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


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
            employeeNotification = accountNotificationRepository.save(employeeNotification);

            String notificationMessage = null;
            try {
                notificationMessage = new ObjectMapper().writeValueAsString(employeeNotification);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            messagingTemplate.convertAndSend("/topic/seller-notifications/"+ employee.getId(), notificationMessage);
        }
    }

    @Override
    public List<AccountNotification> findAllByAccountId(Long accountId) {
        return accountNotificationRepository.findAllByAccountId(accountId);
    }

}
