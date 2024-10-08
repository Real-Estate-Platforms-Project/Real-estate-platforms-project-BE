package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts", schema = "real_estate_platform")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 155)
    @NotNull
    @Column(name = "email", nullable = false, length = 155, unique = true)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false, length = 155)
    private String password;

    @ColumnDefault("0")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    private LocalDateTime updateDay;
    private LocalDateTime expiryDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @PrePersist
    private void setUpdateDayAndExpiryDate(){
        this.updateDay = LocalDateTime.now();
        this.expiryDate = getUpdateDay().plusDays(45);
    }


}