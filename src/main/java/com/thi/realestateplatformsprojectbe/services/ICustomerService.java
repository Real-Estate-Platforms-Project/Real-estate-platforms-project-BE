package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.CustomerDTO;
import jakarta.mail.MessagingException;

public interface ICustomerService {
    void addNewCustomer(CustomerDTO customerDTO) throws MessagingException;

    boolean emailExists(String email);

    void updateCustomerRole(Long customerId, String newRole) throws IllegalArgumentException;

}