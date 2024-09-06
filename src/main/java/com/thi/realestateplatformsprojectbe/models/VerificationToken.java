package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;

    private LocalDateTime expiryDate;

    public VerificationToken() {}

    public VerificationToken(String token, Account account, LocalDateTime expiryDate) {
        this.token = token;
        this.account = account;
        this.expiryDate = expiryDate;
    }
}
