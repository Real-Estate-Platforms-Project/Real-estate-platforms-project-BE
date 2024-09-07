package com.thi.realestateplatformsprojectbe.configs.service;

import com.thi.realestateplatformsprojectbe.configs.UserPrinciple;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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
        return accountRepository.existsByEmail(email);    }


}
