package com.thi.realestateplatformsprojectbe.repositories;
import com.thi.realestateplatformsprojectbe.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

    boolean existsByEmail(String email);

    void findAccountById(Long accountId);
}