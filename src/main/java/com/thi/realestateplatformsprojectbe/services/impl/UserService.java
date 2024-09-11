package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.User;
import com.thi.realestateplatformsprojectbe.repositories.IUserRepository;
import com.thi.realestateplatformsprojectbe.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Override
    public String generateBuyerCode() {
        Random random = new Random();
        String code;
        do {
            int number = random.nextInt(1000000);
            code = String.format("REP-%06d", number);
        } while (isCodeExists(code));

        return code;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getByAccountId(Long accountId) {
        return userRepository.getByAccountId(accountId);
    }

    public boolean isCodeExists(String code) {
        return userRepository.existsByCode(code);
    }
}
