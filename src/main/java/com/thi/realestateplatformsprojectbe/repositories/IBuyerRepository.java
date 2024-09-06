package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBuyerRepository extends JpaRepository<Buyer, Long> {
}