package com.thi.realestateplatformsprojectbe.configs;


import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrinciple implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String accountName;
    private final String password;
    private final Collection<? extends GrantedAuthority> roles;

    public UserPrinciple(String accountName, String password,
                         Collection<? extends GrantedAuthority> roles) {
        this.accountName = accountName;
        this.password = password;
        this.roles = roles;
    }

    public static UserPrinciple build(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : account.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new UserPrinciple(account.getAccountName(),
                account.getPassword(),authorities);
    }

    @Override
    public String getUsername() {
        return accountName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
