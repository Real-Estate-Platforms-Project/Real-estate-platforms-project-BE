package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.VerificationToken;
import com.thi.realestateplatformsprojectbe.repositories.IVerificationTokenRepository;
import com.thi.realestateplatformsprojectbe.services.IVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationTokenService implements IVerificationTokenService {
    @Autowired
    private IVerificationTokenRepository tokenRepository;

    public VerificationToken createVerificationToken(Account account) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(10);
        VerificationToken verificationToken = new VerificationToken(token, account, expiryDate);
        return tokenRepository.save(verificationToken);
    }

    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(VerificationToken token) {
        tokenRepository.delete(token);
    }
}
