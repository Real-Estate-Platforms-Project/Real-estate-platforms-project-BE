package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBuyerRepository extends JpaRepository<Buyer, Long> {
    Buyer findBuyerByAccount_Id(Long id);

    Buyer findByAccount(Account account);


    @Query("SELECT b FROM Buyer b WHERE " +
            "(:code IS NULL OR b.code LIKE %:code%) AND " +
            "(:name IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:email IS NULL OR LOWER(b.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:phoneNumber IS NULL OR b.phoneNumber LIKE %:phoneNumber%)")
    List<Buyer> searchBuyers(@Param("code") String code,
                             @Param("name") String name,
                             @Param("email") String email,
                             @Param("phoneNumber") String phoneNumber);
}