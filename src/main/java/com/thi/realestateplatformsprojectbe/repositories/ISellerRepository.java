package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISellerRepository extends JpaRepository<Seller,Long> {
    Seller findSellerByAccount_Id(Long id);

    Seller findByAccount(Account account);


    @Query("SELECT b FROM Seller b WHERE " +
            "(:code IS NULL OR b.code LIKE %:code%) AND " +
            "(:name IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:email IS NULL OR LOWER(b.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:phoneNumber IS NULL OR b.phoneNumber LIKE %:phoneNumber%)")
    List<Seller> searchSellers(@Param("code") String code,
                             @Param("name") String name,
                             @Param("email") String email,
                             @Param("phoneNumber") String phoneNumber);
}
