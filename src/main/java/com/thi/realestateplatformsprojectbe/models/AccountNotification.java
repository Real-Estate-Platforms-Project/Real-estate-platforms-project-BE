package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="account_notifications")
@Data
@Builder
public class AccountNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean reading;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
