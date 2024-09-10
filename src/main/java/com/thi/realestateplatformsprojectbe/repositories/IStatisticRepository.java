package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IStatisticRepository extends JpaRepository<Demand, Long> {

    @Query("SELECT d " +
            "FROM Demand d " +
            "WHERE YEAR(d.createdAt) = :year " +
            "ORDER BY d.createdAt")
    List<Demand> findDemandByYear(@Param("year") Integer year);

    @Query("SELECT d " +
            "FROM Demand d " +
            "WHERE MONTH(d.createdAt) = :month AND YEAR(d.createdAt) = :year " +
            "ORDER BY d.createdAt")
    List<Demand> findDemandByMonth(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT d " +
            "FROM Demand d " +
            "WHERE DATE(d.createdAt) = :day " +
            "ORDER BY d.createdAt")
    List<Demand> findDemandByDay(@Param("day") Date day);

    @Query("SELECT t " +
            "FROM Transaction t " +
            "WHERE YEAR(t.createAt) = :year " +
            "ORDER BY t.createAt")
    List<Transaction> findTransactionByYear(@Param("year") Integer year);

    @Query("SELECT t " +
            "FROM Transaction t " +
            "WHERE MONTH(t.createAt) = :month AND YEAR(t.createAt) = :year " +
            "ORDER BY t.createAt")
    List<Transaction> findTransactionByMonth(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT t " +
            "FROM Transaction t " +
            "WHERE DATE(t.createAt) = :day " +
            "ORDER BY t.createAt")
    List<Transaction> findTransactionByDay(@Param("day") Date day);
}

