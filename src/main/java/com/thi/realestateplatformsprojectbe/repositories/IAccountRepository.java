package com.thi.realestateplatformsprojectbe.repositories;
import com.thi.realestateplatformsprojectbe.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

    boolean existsByEmail(String email);

    List<Account> findAllByExpiryDateBefore(LocalDateTime now);
}