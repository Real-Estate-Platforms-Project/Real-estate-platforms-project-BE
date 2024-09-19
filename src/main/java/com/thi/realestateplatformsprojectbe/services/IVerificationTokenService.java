package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.VerificationToken;

public interface IVerificationTokenService {
    VerificationToken createVerificationToken(Account account);
    VerificationToken getVerificationToken(String token);
    void deleteToken(VerificationToken token);

    VerificationToken getVerificationTokenByAccount(Account currentAccount);
}