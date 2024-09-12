package com.thi.realestateplatformsprojectbe.configs.service;

import com.thi.realestateplatformsprojectbe.configs.UserPrinciple;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Role;
import com.thi.realestateplatformsprojectbe.models.RoleName;
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


}
